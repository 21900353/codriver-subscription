package codriversubscription.domain;

import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class Unregistered extends AbstractEvent {

    private Long id;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private Date date;
    private Boolean subProcessed;
}
