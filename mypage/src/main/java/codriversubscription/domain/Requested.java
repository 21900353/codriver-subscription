package codriversubscription.domain;

import codriversubscription.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class Requested extends AbstractEvent {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private String date;
    private Boolean subProcessed;
}
