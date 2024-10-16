// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.util.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;

public abstract class AbstractCabinetAggregate extends AbstractAggregate implements CabinetAggregate {
    private CabinetState.MutableCabinetState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractCabinetAggregate(CabinetState state) {
        this.state = (CabinetState.MutableCabinetState)state;
    }

    public CabinetState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void create(CabinetCommand.CreateCabinet c) {
        if (c.getVersion() == null) { c.setVersion(CabinetState.VERSION_NULL); }
        CabinetEvent e = map(c);
        apply(e);
    }

    public void mergePatch(CabinetCommand.MergePatchCabinet c) {
        CabinetEvent e = map(c);
        apply(e);
    }

    public void delete(CabinetCommand.DeleteCabinet c) {
        CabinetEvent e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        CabinetCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected CabinetEvent map(CabinetCommand.CreateCabinet c) {
        CabinetEventId stateEventId = new CabinetEventId(c.getCabinetId(), c.getVersion());
        CabinetEvent.CabinetStateCreated e = newCabinetStateCreated(stateEventId);
        e.setDescription(c.getDescription());
        e.setActive(c.getActive());
        ((AbstractCabinetEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }

    protected CabinetEvent map(CabinetCommand.MergePatchCabinet c) {
        CabinetEventId stateEventId = new CabinetEventId(c.getCabinetId(), c.getVersion());
        CabinetEvent.CabinetStateMergePatched e = newCabinetStateMergePatched(stateEventId);
        e.setDescription(c.getDescription());
        e.setActive(c.getActive());
        e.setIsPropertyDescriptionRemoved(c.getIsPropertyDescriptionRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());
        ((AbstractCabinetEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }

    protected CabinetEvent map(CabinetCommand.DeleteCabinet c) {
        CabinetEventId stateEventId = new CabinetEventId(c.getCabinetId(), c.getVersion());
        CabinetEvent.CabinetStateDeleted e = newCabinetStateDeleted(stateEventId);
        ((AbstractCabinetEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }


    ////////////////////////

    protected CabinetEvent.CabinetStateCreated newCabinetStateCreated(Long version, String commandId, String requesterId) {
        CabinetEventId stateEventId = new CabinetEventId(this.state.getCabinetId(), version);
        CabinetEvent.CabinetStateCreated e = newCabinetStateCreated(stateEventId);
        ((AbstractCabinetEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }

    protected CabinetEvent.CabinetStateMergePatched newCabinetStateMergePatched(Long version, String commandId, String requesterId) {
        CabinetEventId stateEventId = new CabinetEventId(this.state.getCabinetId(), version);
        CabinetEvent.CabinetStateMergePatched e = newCabinetStateMergePatched(stateEventId);
        ((AbstractCabinetEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }

    protected CabinetEvent.CabinetStateDeleted newCabinetStateDeleted(Long version, String commandId, String requesterId) {
        CabinetEventId stateEventId = new CabinetEventId(this.state.getCabinetId(), version);
        CabinetEvent.CabinetStateDeleted e = newCabinetStateDeleted(stateEventId);
        ((AbstractCabinetEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((OffsetDateTime)ApplicationContext.current.getTimestampService().now(OffsetDateTime.class));
        return e;
    }

    protected CabinetEvent.CabinetStateCreated newCabinetStateCreated(CabinetEventId stateEventId) {
        return new AbstractCabinetEvent.SimpleCabinetStateCreated(stateEventId);
    }

    protected CabinetEvent.CabinetStateMergePatched newCabinetStateMergePatched(CabinetEventId stateEventId) {
        return new AbstractCabinetEvent.SimpleCabinetStateMergePatched(stateEventId);
    }

    protected CabinetEvent.CabinetStateDeleted newCabinetStateDeleted(CabinetEventId stateEventId) {
        return new AbstractCabinetEvent.SimpleCabinetStateDeleted(stateEventId);
    }


    public static class SimpleCabinetAggregate extends AbstractCabinetAggregate {
        public SimpleCabinetAggregate(CabinetState state) {
            super(state);
        }

    }

}

