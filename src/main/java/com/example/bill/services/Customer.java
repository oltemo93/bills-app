package com.example.bill.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class Customer {

    @NotNull
    private Long customerId;
    @Schema(accessMode= Schema.AccessMode.READ_ONLY)
    private String firstName;
    @Schema(accessMode= Schema.AccessMode.READ_ONLY)
    private String lastName;

    public Customer() {
        super();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
