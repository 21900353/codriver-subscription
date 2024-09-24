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
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Registered'"
    )
    public void wheneverRegistered_RegisterComplete(
        @Payload Registered registered
    ) {
        Registered event = registered;
        System.out.println(
            "\n\n##### listener RegisterComplete : " + registered + "\n\n"
        );

        // Sample Logic //
        Subscription.registerComplete(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Unregistered'"
    )
    public void wheneverUnregistered_UnregisterComplete(
        @Payload Unregistered unregistered
    ) {
        Unregistered event = unregistered;
        System.out.println(
            "\n\n##### listener UnregisterComplete : " + unregistered + "\n\n"
        );

        // Sample Logic //
        Subscription.unregisterComplete(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
