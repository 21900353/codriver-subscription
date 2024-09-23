package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Unregistered extends AbstractEvent {

    private Long id;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Date date;

    public Unregistered(SubscriptionManagement aggregate) {
        super(aggregate);
    }

    public Unregistered() {
        super();
    }
}
//>>> DDD / Domain Event
