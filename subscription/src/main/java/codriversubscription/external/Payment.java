package codriversubscription.external;

import java.util.Date;
import lombok.Data;

@Data
public class Payment {

    private Long paymentId;
    private Long subId;
    private Long userId;
    private Boolean subStatus;
    private String date;
}
