package com.adidas.subscriptionservice.services;

import com.adidas.subscriptionservice.exceptions.InvalidSubscriptionException;
import com.adidas.subscriptionservice.models.Subscription;
import com.adidas.subscriptionservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by mathias on 22/01/19.
 */


@Service
public class SubscriptionService {

    private static Logger logger = Logger.getLogger(SubscriptionService.class.getName());

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    @Qualifier("mockEmailClientService")
    EmailClientService emailClientService;

    @Autowired
    @Qualifier("mockEventClientService")
    EventClientService eventClientService;

    public Optional<Subscription> getSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    public Subscription saveSubscription(Subscription subscription) throws InvalidSubscriptionException {
        if (Objects.nonNull(subscription.getId()) ||
                Objects.nonNull(subscriptionRepository.findByEmail(subscription.getEmail()))) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Subscription already exists");
            throw new InvalidSubscriptionException(errorMessages);
        }
        validateSubscription(subscription);

        if (emailClientService.sendEmail(subscription)) {
            logger.info("Successfully called email service for subscription email : " + subscription.getEmail());
        }

        if (eventClientService.notifyNewSubscriptionEvent(subscription)) {
            logger.info("Successfully called event service for subscription email : " + subscription.getEmail());
        }

        return subscriptionRepository.saveAndFlush(subscription);
    }

    private void validateSubscription(Subscription subscription) throws InvalidSubscriptionException {
        List<String> errorMessages = new ArrayList<>();
        if (Objects.isNull(subscription.getConsentFlag())) {
            errorMessages.add("Flag of Consent is mandatory");
        }

        if (Objects.isNull(subscription.getDateOfBirth())) {
            errorMessages.add("Date of Birth is mandatory");
        }

        if (Objects.isNull(subscription.getNewsletterId())) {
            errorMessages.add("Newsletter Id is mandatory");
        }

        if (Objects.isNull(subscription.getEmail())) {
            errorMessages.add("Email is mandatory");
        }

        if (errorMessages.size() > 0) {
            throw new InvalidSubscriptionException(errorMessages);
        }
    }

    public Subscription updateSubscription(Subscription subscription) throws InvalidSubscriptionException {
        if (Objects.isNull(subscription.getId())) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Subscription does not exist");
            throw new InvalidSubscriptionException(errorMessages);
        }

        validateSubscription(subscription);

        return subscriptionRepository.saveAndFlush(subscription);
    }

    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

}
