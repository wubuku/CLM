// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.util.*;
import java.math.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.cabinet.CabinetEvent.*;

public abstract class AbstractCabinetState implements CabinetState.SqlCabinetState {

    private String cabinetId;

    public String getCabinetId() {
        return this.cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
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

    public AbstractCabinetState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setCabinetId(((CabinetEvent.SqlCabinetEvent) events.get(0)).getCabinetEventId().getCabinetId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? CabinetState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractCabinetState() {
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
        return getCabinetId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof CabinetState) {
            return Objects.equals(this.getCabinetId(), ((CabinetState)obj).getCabinetId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof CabinetStateCreated) {
            when((CabinetStateCreated) e);
        } else if (e instanceof CabinetStateMergePatched) {
            when((CabinetStateMergePatched) e);
        } else if (e instanceof CabinetStateDeleted) {
            when((CabinetStateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(CabinetStateCreated e) {
        throwOnWrongEvent(e);

        this.setDescription(e.getDescription());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    public void merge(CabinetState s) {
        if (s == this) {
            return;
        }
        this.setDescription(s.getDescription());
        this.setActive(s.getActive());
    }

    public void when(CabinetStateMergePatched e) {
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

    public void when(CabinetStateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(CabinetEvent event) {
        String stateEntityId = this.getCabinetId(); // Aggregate Id
        String eventEntityId = ((CabinetEvent.SqlCabinetEvent)event).getCabinetEventId().getCabinetId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((CabinetEvent.SqlCabinetEvent)event).getCabinetEventId().getVersion();// Event Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getCabinetEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(CabinetState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleCabinetState extends AbstractCabinetState {

        public SimpleCabinetState() {
        }

        public SimpleCabinetState(List<Event> events) {
            super(events);
        }

        public static SimpleCabinetState newForReapplying() {
            SimpleCabinetState s = new SimpleCabinetState();
            s.initializeForReapplying();
            return s;
        }

    }



}

