package com.example.bill.services;

import com.example.bill.repositories.BillRepository;
import com.example.bill.repositories.entities.BillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ElectricityBillImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public void pay(Payment bill) throws CustomerNotFoundException {
        BillEntity billDB = billRepository.findByCustomerId(bill.getBill().getCustomer().getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException());
        BigDecimal newBalance = billDB.getBalance().subtract(bill.getAmountToPay());
        billDB.setBalance(newBalance);
        billRepository.save(billDB);

    }

}
