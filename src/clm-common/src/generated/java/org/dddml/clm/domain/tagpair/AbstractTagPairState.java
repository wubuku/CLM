// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagpair;

import java.util.*;
import java.math.*;
import org.dddml.clm.domain.*;
import java.time.OffsetDateTime;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.tagpair.TagPairEvent.*;

public abstract class AbstractTagPairState implements TagPairState.SqlTagPairState {

    private TagIdPair tagPairId;

    public TagIdPair getTagPairId() {
        return this.tagPairId;
    }

    public void setTagPairId(TagIdPair tagPairId) {
        this.tagPairId = tagPairId;
    }

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private OffsetDateTime createdAt;

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private OffsetDateTime updatedAt;

    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isStateUnsaved() {
        return this.getVersion() == null;
    }

    private Boolean stateReadOnly;

    public Boolean getStateReadOnly() { return this.stateReadOnly; }

    public void setStateReadOnly(Boolean readOnly) { this.stateReadOnly = readOnly; }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    public AbstractTagPairState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setTagPairId(((TagPairEvent.SqlTagPairEvent) events.get(0)).getTagPairEventId().getTagPairId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? TagPairState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractTagPairState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
    }

    @Override
    public int hashCode() {
        return getTagPairId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof TagPairState) {
            return Objects.equals(this.getTagPairId(), ((TagPairState)obj).getTagPairId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof TagPairStateCreated) {
            when((TagPairStateCreated) e);
        } else if (e instanceof TagPairStateMergePatched) {
            when((TagPairStateMergePatched) e);
        } else if (e instanceof TagPairStateDeleted) {
            when((TagPairStateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(TagPairStateCreated e) {
        throwOnWrongEvent(e);

        this.setDescription(e.getDescription());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    public void merge(TagPairState s) {
        if (s == this) {
            return;
        }
        this.setDescription(s.getDescription());
        this.setActive(s.getActive());
    }

    public void when(TagPairStateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getDescription() == null) {
            if (e.getIsPropertyDescriptionRemoved() != null && e.getIsPropertyDescriptionRemoved()) {
                this.setDescription(null);
            }
        } else {
            this.setDescription(e.getDescription());
        }
        if (e.getActive() == null) {
            if (e.getIsPropertyActiveRemoved() != null && e.getIsPropertyActiveRemoved()) {
                this.setActive(null);
            }
        } else {
            this.setActive(e.getActive());
        }

        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void when(TagPairStateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(TagPairEvent event) {
        TagIdPair stateEntityId = this.getTagPairId(); // Aggregate Id
        TagIdPair eventEntityId = ((TagPairEvent.SqlTagPairEvent)event).getTagPairEventId().getTagPairId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((TagPairEvent.SqlTagPairEvent)event).getTagPairEventId().getVersion();// Event Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getTagPairEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(TagPairState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleTagPairState extends AbstractTagPairState {

        public SimpleTagPairState() {
        }

        public SimpleTagPairState(List<Event> events) {
            super(events);
        }

        public static SimpleTagPairState newForReapplying() {
            SimpleTagPairState s = new SimpleTagPairState();
            s.initializeForReapplying();
            return s;
        }

    }



}

