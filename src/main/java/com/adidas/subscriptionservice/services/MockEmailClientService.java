package com.adidas.subscriptionservice.services;

import com.adidas.subscriptionservice.models.Subscription;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by mathias on 24/01/19.
 */

@Service
@Qualifier("mockEmailClientService")
public class MockEmailClientService implements EmailClientService {
    @Override
    public boolean sendEmail(Subscription subscription) {
        return true;
    }
}
