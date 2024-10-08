package codriversubscription.external;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class PayCommand {

    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private String date;
    private String subProcessed;
}
