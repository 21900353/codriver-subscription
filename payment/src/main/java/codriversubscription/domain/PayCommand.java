package codriversubscription.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class PayCommand {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private String date;
    private String subProcessed;
}
