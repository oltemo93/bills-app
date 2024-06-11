package com.example.bill.controllers;

import com.example.bill.services.Bill;
import com.example.bill.services.BillNotFoundException;
import com.example.bill.services.BillService;
import com.example.bill.services.electricity.ElectricityBill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class ElectricityBillController {

    @Autowired
    private BillService billService;

    @Operation(summary = "Add a new bill", description = "Add a new bill", tags={ "bills" })
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBill.class))),
                @ApiResponse(responseCode = "400", description = "Invalid input"),
                @ApiResponse(responseCode = "422", description = "Validation exception") }
        )
    @PostMapping
    public ResponseEntity<?> createBill(@Valid @RequestBody ElectricityBill electricityBill) {
        long billId = billService.create(electricityBill);
        StringBuilder uri = new StringBuilder("bills").append("/").append(billId);
        return ResponseEntity.created(URI.create(uri.toString())).build();
    }

    @Operation(summary = "Get bill information", description = "Get bill information", tags={ "bills" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBill.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception") }
    )
    @GetMapping("/{billId}")
    public ResponseEntity<?> getBill(@PathVariable("billId") Long billId) throws BillNotFoundException {
        return billService.get(billId)
                .map(bill -> ResponseEntity.ok(bill))
                .orElseThrow(() -> new BillNotFoundException("Bill was not found"));

    }

    @Operation(summary = "Get all bills", description = "Get all bills", tags={ "bills" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBill.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception") }
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Bill> bill = billService.findAll();
        return ResponseEntity.ok(bill);
    }

    @Operation(summary = "Delete bill", description = "Delete bill", tags={ "bills" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBill.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception") }
    )
    @DeleteMapping("/{billId}")
    public ResponseEntity<?> deleteBill(@PathVariable("billId") Long billId) {
        billService.delete(billId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update bill", description = "Update bill", tags={ "bills" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectricityBill.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception") }
    )
    @PutMapping("/{billId}")
    public ResponseEntity<?> updateBill(@PathVariable("billId") Long billId, @Valid @RequestBody ElectricityBill electricityBill) throws BillNotFoundException {
        electricityBill.setBillId(billId);
        billService.update(billId, electricityBill);
        return ResponseEntity.noContent().build();
    }

}
