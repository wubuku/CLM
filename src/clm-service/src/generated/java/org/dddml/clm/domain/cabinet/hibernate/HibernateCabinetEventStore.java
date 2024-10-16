// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet.hibernate;

import java.io.Serializable;
import java.util.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.specialization.*;
import org.dddml.clm.specialization.hibernate.AbstractHibernateEventStore;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.clm.domain.cabinet.*;

public class HibernateCabinetEventStore extends AbstractHibernateEventStore {
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new CabinetEventId((String) eventStoreAggregateId.getId(), Long.valueOf(version));
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractCabinetEvent.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractCabinetEvent.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        String idObj = (String) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractCabinetEvent.class);
        criteria.add(Restrictions.eq("cabinetEventId.cabinetId", idObj));
        criteria.add(Restrictions.le("cabinetEventId.version", version));
        criteria.addOrder(Order.asc("cabinetEventId.version"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractCabinetEvent) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractCabinetEvent) es.get(es.size() - 1)).getCabinetEventId().getVersion());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

