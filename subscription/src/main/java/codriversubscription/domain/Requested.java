package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Requested extends AbstractEvent {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Long paymentId;
    private String date;
    private Boolean subProcessed;

    public Requested(Subscription aggregate) {
        super(aggregate);
    }

    public Requested() {
        super();
    }
}
//>>> DDD / Domain Event
