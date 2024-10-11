// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.domain.Command;
import org.dddml.clm.specialization.DomainError;

public interface DeviceCommand extends Command {

    String getDeviceId();

    void setDeviceId(String deviceId);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(DeviceState state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((DeviceCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((DeviceCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(DeviceCommand c) {
        if ((c instanceof DeviceCommand.CreateDevice) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(DeviceState.VERSION_NULL)))
            return true;
        if ((c instanceof DeviceCommand.MergePatchDevice))
            return false;
        if ((c instanceof DeviceCommand.DeleteDevice))
            return false;
        if (c.getVersion().equals(DeviceState.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchDevice extends DeviceCommand
    {

        String getCabinetId();

        void setCabinetId(String cabinetId);

        String getDescription();

        void setDescription(String description);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateDevice extends CreateOrMergePatchDevice
    {
    }

    interface MergePatchDevice extends CreateOrMergePatchDevice
    {
        Boolean getIsPropertyCabinetIdRemoved();

        void setIsPropertyCabinetIdRemoved(Boolean removed);

        Boolean getIsPropertyDescriptionRemoved();

        void setIsPropertyDescriptionRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


    }

    interface DeleteDevice extends DeviceCommand
    {
    }

}

