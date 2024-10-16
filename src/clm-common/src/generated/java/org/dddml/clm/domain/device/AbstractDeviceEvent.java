// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.AbstractEvent;

public abstract class AbstractDeviceEvent extends AbstractEvent implements DeviceEvent.SqlDeviceEvent {
    private DeviceEventId deviceEventId = new DeviceEventId();

    public DeviceEventId getDeviceEventId() {
        return this.deviceEventId;
    }

    public void setDeviceEventId(DeviceEventId eventId) {
        this.deviceEventId = eventId;
    }
    
    public String getDeviceId() {
        return getDeviceEventId().getDeviceId();
    }

    public void setDeviceId(String deviceId) {
        getDeviceEventId().setDeviceId(deviceId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getDeviceEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getDeviceEventId().setVersion(version);
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


    private String commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    private String commandType;

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    protected AbstractDeviceEvent() {
    }

    protected AbstractDeviceEvent(DeviceEventId eventId) {
        this.deviceEventId = eventId;
    }


    public abstract String getEventType();

    public static class DeviceLobEvent extends AbstractDeviceEvent {

        public Map<String, Object> getDynamicProperties() {
            return dynamicProperties;
        }

        public void setDynamicProperties(Map<String, Object> dynamicProperties) {
            if (dynamicProperties == null) {
                throw new IllegalArgumentException("dynamicProperties is null.");
            }
            this.dynamicProperties = dynamicProperties;
        }

        private Map<String, Object> dynamicProperties = new HashMap<>();

        @Override
        public String getEventType() {
            return "DeviceLobEvent";
        }

    }


    public static abstract class AbstractDeviceStateEvent extends AbstractDeviceEvent implements DeviceEvent.DeviceStateEvent {
        private String cabinetId;

        public String getCabinetId()
        {
            return this.cabinetId;
        }

        public void setCabinetId(String cabinetId)
        {
            this.cabinetId = cabinetId;
        }

        private String description;

        public String getDescription()
        {
            return this.description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

        protected AbstractDeviceStateEvent(DeviceEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractDeviceStateCreated extends AbstractDeviceStateEvent implements DeviceEvent.DeviceStateCreated
    {
        public AbstractDeviceStateCreated() {
            this(new DeviceEventId());
        }

        public AbstractDeviceStateCreated(DeviceEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractDeviceStateMergePatched extends AbstractDeviceStateEvent implements DeviceEvent.DeviceStateMergePatched
    {
        public AbstractDeviceStateMergePatched() {
            this(new DeviceEventId());
        }

        public AbstractDeviceStateMergePatched(DeviceEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
        }

        private Boolean isPropertyCabinetIdRemoved;

        public Boolean getIsPropertyCabinetIdRemoved() {
            return this.isPropertyCabinetIdRemoved;
        }

        public void setIsPropertyCabinetIdRemoved(Boolean removed) {
            this.isPropertyCabinetIdRemoved = removed;
        }

        private Boolean isPropertyDescriptionRemoved;

        public Boolean getIsPropertyDescriptionRemoved() {
            return this.isPropertyDescriptionRemoved;
        }

        public void setIsPropertyDescriptionRemoved(Boolean removed) {
            this.isPropertyDescriptionRemoved = removed;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved() {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed) {
            this.isPropertyActiveRemoved = removed;
        }


    }


    public static abstract class AbstractDeviceStateDeleted extends AbstractDeviceStateEvent implements DeviceEvent.DeviceStateDeleted
    {
        public AbstractDeviceStateDeleted() {
            this(new DeviceEventId());
        }

        public AbstractDeviceStateDeleted(DeviceEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

    }

    public static class SimpleDeviceStateCreated extends AbstractDeviceStateCreated
    {
        public SimpleDeviceStateCreated() {
        }

        public SimpleDeviceStateCreated(DeviceEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDeviceStateMergePatched extends AbstractDeviceStateMergePatched
    {
        public SimpleDeviceStateMergePatched() {
        }

        public SimpleDeviceStateMergePatched(DeviceEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDeviceStateDeleted extends AbstractDeviceStateDeleted
    {
        public SimpleDeviceStateDeleted() {
        }

        public SimpleDeviceStateDeleted(DeviceEventId eventId) {
            super(eventId);
        }
    }

}

