package com.example.bill.services;

public interface BillService {

    public void pay(Payment payment) throws CustomerNotFoundException;

}
