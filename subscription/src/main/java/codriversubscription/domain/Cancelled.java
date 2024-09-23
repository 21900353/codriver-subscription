package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Cancelled extends AbstractEvent {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Date date;

    public Cancelled(Subscription aggregate) {
        super(aggregate);
    }

    public Cancelled() {
        super();
    }
}
//>>> DDD / Domain Event
