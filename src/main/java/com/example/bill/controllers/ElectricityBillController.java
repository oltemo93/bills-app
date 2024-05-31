package com.example.bill.controllers;

import com.example.bill.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class ElectricityBillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Response> pay(@RequestBody ElectricityBillPayment body) {
        try {
            billService.pay(body);
        } catch (BillNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404,"Bill was not found"));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, e.getMessage()));
        }
        return ResponseEntity.ok(new SuccessfullResponse(200,"Successfull payment"));
    }

    @GetMapping
    @RequestMapping("/{billId}")
    public ResponseEntity<?> getBalance(@PathVariable("billId") Long billId) {
        Optional<Bill> bill = billService.get(billId);
        if (bill.isPresent()) {
            return ResponseEntity.ok(bill.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "Bill was not found"));
        }
    }

}
