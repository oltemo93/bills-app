package com.example.bill.services;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException() {}

    public CustomerNotFoundException(String message) {
        super(message);
    }

}
