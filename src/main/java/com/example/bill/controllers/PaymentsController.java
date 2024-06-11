package com.example.bill.controllers;

import com.example.bill.services.BillNotFoundException;
import com.example.bill.services.BillService;
import com.example.bill.services.ElectricityBillPayment;
import com.example.bill.services.PaymentException;
import com.example.bill.services.electricity.ElectricityBill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills/{billId}/payments")
public class PaymentsController {

    private BillService billService;

    @Autowired
    public PaymentsController(BillService billService) {
        this.billService = billService;
    }

    @Operation(summary = "Pay a bill", description = "Pay a bill", tags={ "payments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBillPayment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception") }
    )
    @PostMapping
    public ResponseEntity<Response> pay(@PathVariable("billId") Long billId, @Schema(example = "{\"amountToPay\":100}") @RequestBody @Valid ElectricityBillPayment body) {
        ElectricityBill bill = new ElectricityBill();
        bill.setBillId(billId);
        body.setBill(bill);
        try {
            billService.pay(body);
        } catch (BillNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404,"Bill was not found"));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, e.getMessage()));
        }
        return ResponseEntity.ok(new SuccessfullResponse(200,"Successfull payment"));
    }

}
