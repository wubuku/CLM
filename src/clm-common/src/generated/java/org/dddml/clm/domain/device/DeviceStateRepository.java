// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.device;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.util.Date;
import org.dddml.clm.domain.*;

public interface DeviceStateRepository {
    DeviceState get(String id, boolean nullAllowed);

    void save(DeviceState state);

    void merge(DeviceState detached);
}

