package codriversubscription.infra;

import codriversubscription.config.kafka.KafkaProcessor;
import codriversubscription.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionManagementRepository subscriptionManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentMade'"
    )
    public void wheneverPaymentMade_SubscriptionRequested(
        @Payload PaymentMade paymentMade
    ) {
        PaymentMade event = paymentMade;
        System.out.println(
            "\n\n##### listener SubscriptionRequested : " + paymentMade + "\n\n"
        );

        // Sample Logic //
        SubscriptionManagement.subscriptionRequested(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Cancelled'"
    )
    public void wheneverCancelled_SubscriptionCancelled(
        @Payload Cancelled cancelled
    ) {
        Cancelled event = cancelled;
        System.out.println(
            "\n\n##### listener SubscriptionCancelled : " + cancelled + "\n\n"
        );

        // Sample Logic //
        SubscriptionManagement.subscriptionCancelled(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
