package com.inn.smart_reconciliation_api.invoice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.smart_reconciliation_api.configs.entities.exception.GlobalExceptionHandler;
import com.inn.smart_reconciliation_api.controllers.InvoiceController;
import com.inn.smart_reconciliation_api.dtos.InvoiceRequest;
import com.inn.smart_reconciliation_api.dtos.InvoiceResponse;
import com.inn.smart_reconciliation_api.enums.InvoiceStatus;
import com.inn.smart_reconciliation_api.services.InvoiceService;
import com.inn.smart_reconciliation_api.services.impl.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(InvoiceController.class)
@Import({InvoiceController.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setupSecurity() {
        when(customUserDetailsService.loadUserByUsername(any()))
            .thenReturn(User.withUsername("testuser").password("password").roles("USER").build());
    }

    @Test
    void testCreateInvoice() throws Exception {
        InvoiceRequest request = new InvoiceRequest("INV-001", BigDecimal.valueOf(100), InvoiceStatus.PENDING, LocalDate.now(), LocalDate.now(), "Customer A");
        InvoiceResponse response = new InvoiceResponse(1L, "INV-001", BigDecimal.valueOf(100), InvoiceStatus.PENDING, LocalDate.now(), LocalDate.now(), "Customer A");

        when(invoiceService.createInvoice(any())).thenReturn(response);

        mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInvoice() throws Exception {
        when(invoiceService.getInvoice(1L)).thenReturn(new InvoiceResponse(null, null, null, null, null, null, null));

        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllInvoices() throws Exception {
        when(invoiceService.getAllInvoices()).thenReturn(List.of());

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateInvoice() throws Exception {
        InvoiceRequest request = new InvoiceRequest("INV-001", BigDecimal.valueOf(100), InvoiceStatus.PENDING, LocalDate.now(), LocalDate.now(), "Customer A");

        when(invoiceService.updateInvoice(eq(1L), any())).thenReturn(new InvoiceResponse(null, null, null, null, null, null, null));

        mockMvc.perform(put("/api/invoices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteInvoice() throws Exception {
        mockMvc.perform(delete("/api/invoices/1"))
                .andExpect(status().isOk());
    }
}
