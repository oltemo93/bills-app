package com.example.bill.controllers;

import com.example.bill.services.BillNotFoundException;
import com.example.bill.services.BillService;
import com.example.bill.services.ElectricityBillPayment;
import com.example.bill.services.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private PaymentsController paymentsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentsController).build();
    }

    @Test
    public void testPaySuccess() throws Exception {

        mockMvc.perform(post("/bills/1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amountToPay\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("Successfull payment"));
    }

    @Test
    public void testPayBillNotFound() throws Exception {
        doThrow(new BillNotFoundException("Bill was not found")).when(billService).pay(any(ElectricityBillPayment.class));

        mockMvc.perform(post("/bills/1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amountToPay\": 100.0}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bill was not found"));
    }

    @Test
    public void testPayPaymentException() throws Exception {
        doThrow(new PaymentException("Payment error")).when(billService).pay(any(ElectricityBillPayment.class));

        String requestBody = """
                {
                    "amountToPay":100
                }
                """;

        mockMvc.perform(post("/bills/1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Payment error"));
    }

}