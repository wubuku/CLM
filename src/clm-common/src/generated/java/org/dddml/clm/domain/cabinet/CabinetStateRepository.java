// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;

public interface CabinetStateRepository {
    CabinetState get(String id, boolean nullAllowed);

    void save(CabinetState state);

    void merge(CabinetState detached);
}

