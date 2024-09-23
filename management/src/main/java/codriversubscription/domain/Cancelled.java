package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Cancelled extends AbstractEvent {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Long paymentId;
    private String date;
    private Boolean subProcessed;
}
