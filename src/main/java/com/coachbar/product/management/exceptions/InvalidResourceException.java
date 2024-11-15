package com.coachbar.product.management.exceptions;

public class InvalidResourceException extends RuntimeException{
    String msg;

    public InvalidResourceException(String msg) {
        super(msg);
    }
}
