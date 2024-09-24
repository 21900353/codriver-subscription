package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentMade extends AbstractEvent {

    private Long paymentId;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private String date;
    private String subProcessed;

    public PaymentMade(Payment aggregate) {
        super(aggregate);
    }

    public PaymentMade() {
        super();
    }
}
//>>> DDD / Domain Event
