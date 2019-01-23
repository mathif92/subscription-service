package com.adidas.subscriptionservice.repositories;

import com.adidas.subscriptionservice.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mathias on 22/01/19.
 */


public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
