package com.example.bill.controllers;

public class ErrorResponse extends Response {

    private String message;

    public ErrorResponse(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
