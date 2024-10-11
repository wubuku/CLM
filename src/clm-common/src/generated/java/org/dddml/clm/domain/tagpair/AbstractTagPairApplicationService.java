// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagpair;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import org.dddml.clm.domain.*;
import java.time.OffsetDateTime;
import org.dddml.clm.specialization.*;

public abstract class AbstractTagPairApplicationService implements TagPairApplicationService {

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private TagPairStateRepository stateRepository;

    protected TagPairStateRepository getStateRepository() {
        return stateRepository;
    }

    private TagPairStateQueryRepository stateQueryRepository;

    protected TagPairStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<TagPairAggregate, TagPairState> aggregateEventListener;

    public AggregateEventListener<TagPairAggregate, TagPairState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<TagPairAggregate, TagPairState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractTagPairApplicationService(EventStore eventStore, TagPairStateRepository stateRepository, TagPairStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(TagPairCommand.CreateTagPair c) {
        update(c, ar -> ar.create(c));
    }

    public void when(TagPairCommand.MergePatchTagPair c) {
        update(c, ar -> ar.mergePatch(c));
    }

    public void when(TagPairCommand.DeleteTagPair c) {
        update(c, ar -> ar.delete(c));
    }

    public TagPairState get(TagIdPair id) {
        TagPairState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<TagPairState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<TagPairState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<TagPairState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<TagPairState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public TagPairEvent getEvent(TagIdPair tagPairId, long version) {
        TagPairEvent e = (TagPairEvent)getEventStore().getEvent(toEventStoreAggregateId(tagPairId), version);
        if (e != null) {
            ((TagPairEvent.SqlTagPairEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(tagPairId, 0);
        }
        return e;
    }

    public TagPairState getHistoryState(TagIdPair tagPairId, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractTagPairEvent.class, toEventStoreAggregateId(tagPairId), version - 1);
        return new AbstractTagPairState.SimpleTagPairState(eventStream.getEvents());
    }


    public TagPairAggregate getTagPairAggregate(TagPairState state) {
        return new AbstractTagPairAggregate.SimpleTagPairAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(TagIdPair aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(TagPairCommand c, Consumer<TagPairAggregate> action) {
        TagIdPair aggregateId = c.getTagPairId();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        TagPairState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        TagPairAggregate aggregate = getTagPairAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getVersion() == null ? TagPairState.VERSION_NULL : c.getVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, TagPairAggregate aggregate, TagPairState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    public void initialize(TagPairEvent.TagPairStateCreated stateCreated) {
        TagIdPair aggregateId = ((TagPairEvent.SqlTagPairEvent)stateCreated).getTagPairEventId().getTagPairId();
        TagPairState.SqlTagPairState state = new AbstractTagPairState.SimpleTagPairState();
        state.setTagPairId(aggregateId);

        TagPairAggregate aggregate = getTagPairAggregate(state);
        ((AbstractTagPairAggregate) aggregate).apply(stateCreated);

        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        persist(eventStoreAggregateId, ((TagPairEvent.SqlTagPairEvent)stateCreated).getTagPairEventId().getVersion(), aggregate, state);
    }

    protected boolean isDuplicateCommand(TagPairCommand command, EventStoreAggregateId eventStoreAggregateId, TagPairState state) {
        boolean duplicate = false;
        if (command.getVersion() == null) { command.setVersion(TagPairState.VERSION_NULL); }
        if (state.getVersion() != null && state.getVersion() > command.getVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractTagPairEvent.class, eventStoreAggregateId, command.getVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleTagPairApplicationService extends AbstractTagPairApplicationService {
        public SimpleTagPairApplicationService(EventStore eventStore, TagPairStateRepository stateRepository, TagPairStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

