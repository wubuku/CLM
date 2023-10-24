// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import java.util.Date;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.Event;

public interface DeviceEvent extends Event {

    interface SqlDeviceEvent extends DeviceEvent {
        DeviceEventId getDeviceEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getDeviceId();

    //void setDeviceId(String deviceId);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface DeviceStateEvent extends DeviceEvent {
        String getCabinetId();

        void setCabinetId(String cabinetId);

        String getDescription();

        void setDescription(String description);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface DeviceStateCreated extends DeviceStateEvent
    {
    
    }


    interface DeviceStateMergePatched extends DeviceStateEvent
    {
        Boolean getIsPropertyCabinetIdRemoved();

        void setIsPropertyCabinetIdRemoved(Boolean removed);

        Boolean getIsPropertyDescriptionRemoved();

        void setIsPropertyDescriptionRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface DeviceStateDeleted extends DeviceStateEvent
    {
    }


}

