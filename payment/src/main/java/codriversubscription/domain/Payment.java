package codriversubscription.domain;

import codriversubscription.PaymentApplication;
import codriversubscription.domain.PaymentMade;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Payment_table")
@Data
//<<< DDD / Aggregate Root
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    private Long subId;

    private Long userId;

    private Boolean subStatus;

    @PostPersist
    public void onPostPersist() {
        PaymentMade paymentMade = new PaymentMade(this);
        paymentMade.publishAfterCommit();
    }

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    public void pay() {
        //implement business logic here:

    }
}
//>>> DDD / Aggregate Root
