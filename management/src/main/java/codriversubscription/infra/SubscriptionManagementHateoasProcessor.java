package codriversubscription.infra;

import codriversubscription.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionManagementHateoasProcessor
    implements
        RepresentationModelProcessor<EntityModel<SubscriptionManagement>> {

    @Override
    public EntityModel<SubscriptionManagement> process(
        EntityModel<SubscriptionManagement> model
    ) {
        return model;
    }
}
