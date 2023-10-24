// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.util.Date;
import org.dddml.clm.domain.*;

public interface CabinetStateQueryRepository {
    CabinetState get(String id);

    Iterable<CabinetState> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<CabinetState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<CabinetState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    CabinetState getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    CabinetState getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<CabinetState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

}

