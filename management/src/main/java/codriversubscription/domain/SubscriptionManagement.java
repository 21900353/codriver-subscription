package codriversubscription.domain;

import codriversubscription.ManagementApplication;
import codriversubscription.domain.Registered;
import codriversubscription.domain.Unregistered;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SubscriptionManagement_table")
@Data
//<<< DDD / Aggregate Root
public class SubscriptionManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long subId;

    private Long userId;

    private Boolean subStatus;

    private String date;

    private Boolean subProcessed;

    @PostUpdate
    public void onPostUpdate() {
        Registered registered = new Registered(this);
        registered.publishAfterCommit();

        Unregistered unregistered = new Unregistered(this);
        unregistered.publishAfterCommit();
    }

    public static SubscriptionManagementRepository repository() {
        SubscriptionManagementRepository subscriptionManagementRepository = ManagementApplication.applicationContext.getBean(
            SubscriptionManagementRepository.class
        );
        return subscriptionManagementRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscriptionRequested(PaymentMade paymentMade) {
        //implement business logic here:

        /** Example 1:  new item 
        SubscriptionManagement subscriptionManagement = new SubscriptionManagement();
        repository().save(subscriptionManagement);

        */

        /** Example 2:  finding and process
        
        repository().findById(paymentMade.get???()).ifPresent(subscriptionManagement->{
            
            subscriptionManagement // do something
            repository().save(subscriptionManagement);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscriptionCancelled(Cancelled cancelled) {
        //implement business logic here:

        /** Example 1:  new item 
        SubscriptionManagement subscriptionManagement = new SubscriptionManagement();
        repository().save(subscriptionManagement);

        */

        /** Example 2:  finding and process
        
        repository().findById(cancelled.get???()).ifPresent(subscriptionManagement->{
            
            subscriptionManagement // do something
            repository().save(subscriptionManagement);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
