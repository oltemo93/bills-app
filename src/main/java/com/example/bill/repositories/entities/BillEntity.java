package com.example.bill.repositories.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="electricity_bill")
public class BillEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="customer_id")
    private Long customerId;
    @Column(name="balance")
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
