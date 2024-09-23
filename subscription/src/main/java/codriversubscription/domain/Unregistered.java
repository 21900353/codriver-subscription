package codriversubscription.domain;

import codriversubscription.domain.*;
import codriversubscription.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Unregistered extends AbstractEvent {

    private Long id;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Date date;
}
