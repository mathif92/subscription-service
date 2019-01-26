package com.adidas.subscriptionservice.exceptions;

import com.adidas.subscriptionservice.controllers.SubscriptionController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mathias on 26/01/19.
 */


@ControllerAdvice(assignableTypes = SubscriptionController.class)
@RequestMapping(produces = "application/json")
public class ApplicationAdvice {

    @ExceptionHandler(InvalidSubscriptionException.class)
    public ResponseEntity invalidSubscriptionExceptionHandler(final InvalidSubscriptionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.errorMessages);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
