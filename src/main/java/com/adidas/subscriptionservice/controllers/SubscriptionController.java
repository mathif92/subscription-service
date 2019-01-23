package com.adidas.subscriptionservice.controllers;

import com.adidas.subscriptionservice.exceptions.InvalidSubscriptionException;
import com.adidas.subscriptionservice.models.Subscription;
import com.adidas.subscriptionservice.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by mathias on 22/01/19.
 */

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private static Logger logger = Logger.getLogger(SubscriptionController.class.getName());

    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping(value = "/{subscriptionId}", produces = "application/json")
    public ResponseEntity getSubscription(@PathVariable Long subscriptionId) {
        try {
            Optional<Subscription> subscription = subscriptionService.getSubscription(subscriptionId);
            if (subscription.isPresent()) {
                return ResponseEntity.ok(subscription);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity saveSubscription(@RequestBody Subscription subscription) {
        try {
            subscription = subscriptionService.saveSubscription(subscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(subscription.getId());
        } catch (InvalidSubscriptionException ise) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ise.errorMessages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping(value = "", produces = "application/json")
    public ResponseEntity fullyUpdateSubscription(@RequestBody Subscription subscription) {
        try {
            subscription = subscriptionService.updateSubscription(subscription);
            return ResponseEntity.ok(subscription.getId());
        } catch (InvalidSubscriptionException ise) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ise.errorMessages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping(value = "/{subscriptionId}", produces = "application/json")
    public ResponseEntity fullyUpdateSubscription(@PathVariable Long subscriptionId) {
        try {
            logger.info("Request for deleting subscription : " + subscriptionId);
            if (subscriptionService.getSubscription(subscriptionId).isPresent()) {
                subscriptionService.deleteSubscription(subscriptionId);

                logger.info("Subscription : " + subscriptionId + " was deleted successfully");
                return ResponseEntity.ok("Subscription was deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Subscription does not exist");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
