// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.util.List;
import java.util.Date;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.Event;
import org.dddml.clm.domain.Command;

public interface CabinetAggregate {
    CabinetState getState();

    List<Event> getChanges();

    void create(CabinetCommand.CreateCabinet c);

    void mergePatch(CabinetCommand.MergePatchCabinet c);

    void delete(CabinetCommand.DeleteCabinet c);

    void throwOnInvalidStateTransition(Command c);
}

