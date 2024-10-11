// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import java.math.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.device.DeviceEvent.*;

public abstract class AbstractDeviceState implements DeviceState.SqlDeviceState {

    private String deviceId;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public AbstractDeviceState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setDeviceId(((DeviceEvent.SqlDeviceEvent) events.get(0)).getDeviceEventId().getDeviceId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? DeviceState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractDeviceState() {
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
        return getDeviceId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof DeviceState) {
            return Objects.equals(this.getDeviceId(), ((DeviceState)obj).getDeviceId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof DeviceStateCreated) {
            when((DeviceStateCreated) e);
        } else if (e instanceof DeviceStateMergePatched) {
            when((DeviceStateMergePatched) e);
        } else if (e instanceof DeviceStateDeleted) {
            when((DeviceStateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(DeviceStateCreated e) {
        throwOnWrongEvent(e);

        this.setCabinetId(e.getCabinetId());
        this.setDescription(e.getDescription());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    public void merge(DeviceState s) {
        if (s == this) {
            return;
        }
        this.setCabinetId(s.getCabinetId());
        this.setDescription(s.getDescription());
        this.setActive(s.getActive());
    }

    public void when(DeviceStateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getCabinetId() == null) {
            if (e.getIsPropertyCabinetIdRemoved() != null && e.getIsPropertyCabinetIdRemoved()) {
                this.setCabinetId(null);
            }
        } else {
            this.setCabinetId(e.getCabinetId());
        }
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

    public void when(DeviceStateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(DeviceEvent event) {
        String stateEntityId = this.getDeviceId(); // Aggregate Id
        String eventEntityId = ((DeviceEvent.SqlDeviceEvent)event).getDeviceEventId().getDeviceId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((DeviceEvent.SqlDeviceEvent)event).getDeviceEventId().getVersion();// Event Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getDeviceEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(DeviceState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleDeviceState extends AbstractDeviceState {

        public SimpleDeviceState() {
        }

        public SimpleDeviceState(List<Event> events) {
            super(events);
        }

        public static SimpleDeviceState newForReapplying() {
            SimpleDeviceState s = new SimpleDeviceState();
            s.initializeForReapplying();
            return s;
        }

    }



}

