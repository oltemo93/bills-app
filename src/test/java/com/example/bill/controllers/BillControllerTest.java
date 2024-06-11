package com.example.bill.controllers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.example.bill.services.Bill;
import com.example.bill.services.BillService;
import com.example.bill.services.electricity.ElectricityBill;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

public class BillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private ElectricityBillController billController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(billController).build();
    }

    @Test
    public void testGetBillSuccess() throws Exception {
        Bill bill = new ElectricityBill();
        when(billService.get(anyLong())).thenReturn(Optional.of(bill));

        mockMvc.perform(get("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBillNotFound() throws Exception {
        when(billService.get(anyLong())).thenReturn(Optional.empty());

        standaloneSetup(billController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build()
                .perform(get("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bill was not found"));
    }

    @Test
    public void testCreateBill() throws Exception {
        when(billService.create(ArgumentMatchers.any(ElectricityBill.class))).thenReturn(1L);

        String request = """
                {
                  "customer": {
                    "customerId": 0
                  },
                  "periodStart": "2024-06-10",
                  "periodEnd": "2024-06-10",
                  "serviceAddress": "string",
                  "type": "electricity",
                  "costPerKhw": 0,
                  "totalKhwConsumed": 0
                }
                """;

        mockMvc.perform(post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "bills/1"));

        verify(billService, times(1)).create(any(ElectricityBill.class));
    }

    @Test
    public void testFindAll() throws Exception {
        Bill bill1 = new ElectricityBill();
        Bill bill2 = new ElectricityBill();
        when(billService.findAll()).thenReturn(Arrays.asList(bill1, bill2));

        mockMvc.perform(get("/bills")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)));

        verify(billService, times(1)).findAll();
    }

    @Test
    public void testDeleteBill() throws Exception {
        doNothing().when(billService).delete(anyLong());

        mockMvc.perform(delete("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(billService, times(1)).delete(anyLong());
    }

    @Test
    public void testUpdateBill() throws Exception {
        doNothing().when(billService).update(anyLong(), any(ElectricityBill.class));

        String request = """
                {
                   "customer": {
                     "customerId": 0
                   },
                   "periodStart": "2024-06-10",
                   "periodEnd": "2024-06-10",
                   "serviceAddress": "string",
                   "type": "electricity",
                   "costPerKhw": 0,
                   "totalKhwConsumed": 0
                 }
                """;

        mockMvc.perform(put("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNoContent());

        verify(billService, times(1)).update(anyLong(), any(ElectricityBill.class));
    }
}
