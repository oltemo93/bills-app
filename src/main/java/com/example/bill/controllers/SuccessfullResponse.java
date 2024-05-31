package com.example.bill.controllers;

public class SuccessfullResponse extends Response {

    private String body;

    public SuccessfullResponse(int statusCode, String message) {
        super(statusCode);
        this.body = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
