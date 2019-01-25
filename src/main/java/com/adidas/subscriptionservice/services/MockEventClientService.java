package com.adidas.subscriptionservice.services;

import com.adidas.subscriptionservice.models.Subscription;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by mathias on 24/01/19.
 */

@Service
@Qualifier("mockEventClientService")
public class MockEventClientService implements EventClientService {

    @Override
    public boolean notifyNewSubscriptionEvent(Subscription subscription) {
        return true;
    }
}
