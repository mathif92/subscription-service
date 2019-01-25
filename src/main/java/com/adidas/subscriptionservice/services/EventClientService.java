package com.adidas.subscriptionservice.services;

import com.adidas.subscriptionservice.models.Subscription;

/**
 * Created by mathias on 24/01/19.
 */
public interface EventClientService {

    boolean notifyNewSubscriptionEvent(Subscription subscription);

}
