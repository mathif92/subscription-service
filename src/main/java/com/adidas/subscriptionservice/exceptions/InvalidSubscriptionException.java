package com.adidas.subscriptionservice.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathias on 22/01/19.
 */
public class InvalidSubscriptionException extends Exception {

    public List<String> errorMessages = new ArrayList<>();

    public InvalidSubscriptionException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public InvalidSubscriptionException() {

    }

}
