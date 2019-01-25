package com.adidas.subscriptionservice.repositories;

import com.adidas.subscriptionservice.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by mathias on 22/01/19.
 */


public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select sub from Subscription sub where sub.email = :email ")
    Subscription findByEmail(@Param("email") String email);

}
