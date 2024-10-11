// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagdeviceassociation;

import java.util.*;
import org.dddml.clm.domain.*;
import java.time.OffsetDateTime;
import org.dddml.clm.specialization.*;
import org.dddml.clm.domain.AbstractEvent;

public abstract class AbstractTagDeviceAssociationEvent extends AbstractEvent implements TagDeviceAssociationEvent.SqlTagDeviceAssociationEvent {
    private TagDeviceAssociationEventId tagDeviceAssociationEventId = new TagDeviceAssociationEventId();

    public TagDeviceAssociationEventId getTagDeviceAssociationEventId() {
        return this.tagDeviceAssociationEventId;
    }

    public void setTagDeviceAssociationEventId(TagDeviceAssociationEventId eventId) {
        this.tagDeviceAssociationEventId = eventId;
    }
    
    public TagDeviceAssociationId getTagDeviceAssociationId() {
        return getTagDeviceAssociationEventId().getTagDeviceAssociationId();
    }

    public void setTagDeviceAssociationId(TagDeviceAssociationId tagDeviceAssociationId) {
        getTagDeviceAssociationEventId().setTagDeviceAssociationId(tagDeviceAssociationId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getTagDeviceAssociationEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getTagDeviceAssociationEventId().setVersion(version);
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

    protected AbstractTagDeviceAssociationEvent() {
    }

    protected AbstractTagDeviceAssociationEvent(TagDeviceAssociationEventId eventId) {
        this.tagDeviceAssociationEventId = eventId;
    }


    public abstract String getEventType();

    public static class TagDeviceAssociationLobEvent extends AbstractTagDeviceAssociationEvent {

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
            return "TagDeviceAssociationLobEvent";
        }

    }


    public static abstract class AbstractTagDeviceAssociationStateEvent extends AbstractTagDeviceAssociationEvent implements TagDeviceAssociationEvent.TagDeviceAssociationStateEvent {
        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

        protected AbstractTagDeviceAssociationStateEvent(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractTagDeviceAssociationStateCreated extends AbstractTagDeviceAssociationStateEvent implements TagDeviceAssociationEvent.TagDeviceAssociationStateCreated
    {
        public AbstractTagDeviceAssociationStateCreated() {
            this(new TagDeviceAssociationEventId());
        }

        public AbstractTagDeviceAssociationStateCreated(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractTagDeviceAssociationStateMergePatched extends AbstractTagDeviceAssociationStateEvent implements TagDeviceAssociationEvent.TagDeviceAssociationStateMergePatched
    {
        public AbstractTagDeviceAssociationStateMergePatched() {
            this(new TagDeviceAssociationEventId());
        }

        public AbstractTagDeviceAssociationStateMergePatched(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved() {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed) {
            this.isPropertyActiveRemoved = removed;
        }


    }


    public static abstract class AbstractTagDeviceAssociationStateDeleted extends AbstractTagDeviceAssociationStateEvent implements TagDeviceAssociationEvent.TagDeviceAssociationStateDeleted
    {
        public AbstractTagDeviceAssociationStateDeleted() {
            this(new TagDeviceAssociationEventId());
        }

        public AbstractTagDeviceAssociationStateDeleted(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

    }

    public static class SimpleTagDeviceAssociationStateCreated extends AbstractTagDeviceAssociationStateCreated
    {
        public SimpleTagDeviceAssociationStateCreated() {
        }

        public SimpleTagDeviceAssociationStateCreated(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleTagDeviceAssociationStateMergePatched extends AbstractTagDeviceAssociationStateMergePatched
    {
        public SimpleTagDeviceAssociationStateMergePatched() {
        }

        public SimpleTagDeviceAssociationStateMergePatched(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleTagDeviceAssociationStateDeleted extends AbstractTagDeviceAssociationStateDeleted
    {
        public SimpleTagDeviceAssociationStateDeleted() {
        }

        public SimpleTagDeviceAssociationStateDeleted(TagDeviceAssociationEventId eventId) {
            super(eventId);
        }
    }

}

