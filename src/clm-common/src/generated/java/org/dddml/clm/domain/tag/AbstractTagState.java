// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tag;

import java.util.*;
import java.math.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.tag.TagEvent.*;

public abstract class AbstractTagState implements TagState.SqlTagState {

    private String tagId;

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    private Integer status;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public AbstractTagState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setTagId(((TagEvent.SqlTagEvent) events.get(0)).getTagEventId().getTagId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? TagState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractTagState() {
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
        return getTagId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof TagState) {
            return Objects.equals(this.getTagId(), ((TagState)obj).getTagId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof TagStateCreated) {
            when((TagStateCreated) e);
        } else if (e instanceof TagStateMergePatched) {
            when((TagStateMergePatched) e);
        } else if (e instanceof TagStateDeleted) {
            when((TagStateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(TagStateCreated e) {
        throwOnWrongEvent(e);

        this.setStatus(e.getStatus());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    public void merge(TagState s) {
        if (s == this) {
            return;
        }
        this.setStatus(s.getStatus());
        this.setActive(s.getActive());
    }

    public void when(TagStateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getStatus() == null) {
            if (e.getIsPropertyStatusRemoved() != null && e.getIsPropertyStatusRemoved()) {
                this.setStatus(null);
            }
        } else {
            this.setStatus(e.getStatus());
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

    public void when(TagStateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(TagEvent event) {
        String stateEntityId = this.getTagId(); // Aggregate Id
        String eventEntityId = ((TagEvent.SqlTagEvent)event).getTagEventId().getTagId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((TagEvent.SqlTagEvent)event).getTagEventId().getVersion();// Event Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getTagEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(TagState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleTagState extends AbstractTagState {

        public SimpleTagState() {
        }

        public SimpleTagState(List<Event> events) {
            super(events);
        }

        public static SimpleTagState newForReapplying() {
            SimpleTagState s = new SimpleTagState();
            s.initializeForReapplying();
            return s;
        }

    }



}

