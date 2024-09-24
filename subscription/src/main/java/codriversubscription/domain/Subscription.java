package codriversubscription.domain;

import codriversubscription.SubscriptionApplication;
import codriversubscription.domain.Cancelled;
import codriversubscription.domain.Requested;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Subscription_table")
@Data
//<<< DDD / Aggregate Root
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subId;

    private Long userId;

    private Boolean subStatus;

    private String date;

    private Boolean subProcessed = false;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        codriversubscription.external.PayCommand payCommand = new codriversubscription.external.PayCommand();
        // mappings goes here
        SubscriptionApplication.applicationContext
            .getBean(codriversubscription.external.PaymentService.class)
            .pay(subId, payCommand);

        Requested requested = new Requested(this);
        requested.publishAfterCommit();

        Cancelled cancelled = new Cancelled(this);
        cancelled.publishAfterCommit();
    }

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }

    public void request() {
        //implement business logic here:

        Requested requested = new Requested(this);
        requested.publishAfterCommit();
    }

    public void cancel() {
        //implement business logic here:

        Cancelled cancelled = new Cancelled(this);
        cancelled.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void registerComplete(Registered registered) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        
        repository().findById(registered.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void unregisterComplete(Unregistered unregistered) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        
        repository().findById(unregistered.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
