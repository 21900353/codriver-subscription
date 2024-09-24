package codriversubscription.domain;

import codriversubscription.PaymentApplication;
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

    private String date;

    private String subProcessed;

    @PostPersist
    public void onPostPersist() {}

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    //<<< Clean Arch / Port Method
    public void pay(PayCommand payCommand) {
        //implement business logic here:

        PaymentMade paymentMade = new PaymentMade(this);
        paymentMade.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
