package com.adidas.subscriptionservice.controllers;

import com.adidas.subscriptionservice.exceptions.InvalidSubscriptionException;
import com.adidas.subscriptionservice.models.Subscription;
import com.adidas.subscriptionservice.responses.MessageResponse;
import com.adidas.subscriptionservice.responses.SubscriptionCreatedResponse;
import com.adidas.subscriptionservice.services.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="/subscriptions",description="Subscriptions endpoints",produces ="application/json")
public class SubscriptionController {

    private static Logger logger = Logger.getLogger(SubscriptionController.class.getName());

    @Autowired
    SubscriptionService subscriptionService;

    @ApiOperation(value="get subscription",response=ResponseEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Subscription Retrieved",response=Subscription.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=401,message="Unauthorized"),
            @ApiResponse(code=404,message="Subscription not found")
    })
    @GetMapping(value = "/{subscriptionId}", produces = "application/json")
    public ResponseEntity getSubscription(@PathVariable Long subscriptionId) {
        Optional<Subscription> subscription = subscriptionService.getSubscription(subscriptionId);
        if (subscription.isPresent()) {
            return ResponseEntity.ok(subscription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value="create subscription",response=ResponseEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=201,message="Subscription Created",response=SubscriptionCreatedResponse.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=401,message="Unauthorized"),
            @ApiResponse(code=401,message="Bad Request")
    })
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity saveSubscription(@RequestBody Subscription subscription) throws InvalidSubscriptionException {
        subscription = subscriptionService.saveSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SubscriptionCreatedResponse(subscription.getId()));
    }

    @ApiOperation(value="update subscription",response=ResponseEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Subscription Updated",response=Subscription.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=401,message="Unauthorized"),
            @ApiResponse(code=401,message="Bad Request")
    })
    @PutMapping(value = "", produces = "application/json")
    public ResponseEntity fullyUpdateSubscription(@RequestBody Subscription subscription) throws InvalidSubscriptionException {
        subscription = subscriptionService.updateSubscription(subscription);
        return ResponseEntity.ok(subscription);
    }

    @ApiOperation(value="delete subscription",response=ResponseEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Subscription Deleted",response=MessageResponse.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=401,message="Unauthorized"),
            @ApiResponse(code=400,message="Bad Request")
    })
    @DeleteMapping(value = "/{subscriptionId}", produces = "application/json")
    public ResponseEntity fullyUpdateSubscription(@PathVariable Long subscriptionId) {
        logger.info("Request for deleting subscription : " + subscriptionId);
        if (subscriptionService.getSubscription(subscriptionId).isPresent()) {
            subscriptionService.deleteSubscription(subscriptionId);

            logger.info("Subscription : " + subscriptionId + " was deleted successfully");
            return ResponseEntity.ok(new MessageResponse("The suscription was deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Subscription does not exist"));
        }
    }

}
