// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagdeviceassociation;

import java.util.*;
import org.dddml.clm.domain.*;
import java.util.Date;
import org.dddml.clm.specialization.Event;

public interface TagDeviceAssociationEvent extends Event {

    interface SqlTagDeviceAssociationEvent extends TagDeviceAssociationEvent {
        TagDeviceAssociationEventId getTagDeviceAssociationEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    TagDeviceAssociationId getTagDeviceAssociationId();

    //void setTagDeviceAssociationId(TagDeviceAssociationId tagDeviceAssociationId);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface TagDeviceAssociationStateEvent extends TagDeviceAssociationEvent {
        Boolean getActive();

        void setActive(Boolean active);

    }

    interface TagDeviceAssociationStateCreated extends TagDeviceAssociationStateEvent
    {
    
    }


    interface TagDeviceAssociationStateMergePatched extends TagDeviceAssociationStateEvent
    {
        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface TagDeviceAssociationStateDeleted extends TagDeviceAssociationStateEvent
    {
    }


}

