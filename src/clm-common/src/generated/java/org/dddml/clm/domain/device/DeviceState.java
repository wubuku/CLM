// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import java.math.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.Event;

public interface DeviceState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getDeviceId();

    String getCabinetId();

    String getDescription();

    Long getVersion();

    String getCreatedBy();

    OffsetDateTime getCreatedAt();

    String getUpdatedBy();

    OffsetDateTime getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    interface MutableDeviceState extends DeviceState {
        void setDeviceId(String deviceId);

        void setCabinetId(String cabinetId);

        void setDescription(String description);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(OffsetDateTime createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(OffsetDateTime updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);


        void mutate(Event e);

        //void when(DeviceEvent.DeviceStateCreated e);

        //void when(DeviceEvent.DeviceStateMergePatched e);

        //void when(DeviceEvent.DeviceStateDeleted e);
    }

    interface SqlDeviceState extends MutableDeviceState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

