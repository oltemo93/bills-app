package com.example.bill.controllers;

import com.example.bill.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bills/{billId}")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<?> pay(@PathVariable("billId") Long billId, @RequestBody Payment body) {
        try {
            billService.pay(billId, body);
        } catch (BillNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Bill was not found"));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessage("Successfull payment"));
    }

    @GetMapping
    public ResponseEntity<?> get(@PathVariable("billId") Long billId) {
        Optional<Bill> bill = billService.get(billId);
        if (bill.isPresent()) {
            return ResponseEntity.ok(bill.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Bill was not found"));
        }
    }

}
