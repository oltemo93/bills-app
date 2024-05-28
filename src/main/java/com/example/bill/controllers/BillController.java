package com.example.bill.controllers;

import com.example.bill.services.BillService;
import com.example.bill.services.CustomerNotFoundException;
import com.example.bill.services.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody Payment body) {
        try {
            billService.pay(body);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Customer was not found"));
        }
        return ResponseEntity.ok(new ResponseMessage("Successfull payment"));
    }

}
