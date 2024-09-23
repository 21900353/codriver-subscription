package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PaymentMade extends AbstractEvent {

    private Long paymentId;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
}
