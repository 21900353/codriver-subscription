package codriversubscription.domain;

import codriversubscription.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscriptionManagements",
    path = "subscriptionManagements"
)
public interface SubscriptionManagementRepository
    extends PagingAndSortingRepository<SubscriptionManagement, Long> {}
