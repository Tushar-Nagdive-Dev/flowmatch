package com.inn.smart_reconciliation_api.reconciliation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
import com.inn.smart_reconciliation_api.controllers.ReconciliationController;
import com.inn.smart_reconciliation_api.dtos.ReconciliationRequest;
import com.inn.smart_reconciliation_api.dtos.ReconciliationResponse;
import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;
import com.inn.smart_reconciliation_api.services.ReconciliationService;
import com.inn.smart_reconciliation_api.services.impl.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReconciliationController.class)
@Import({ReconciliationController.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class ReconciliationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ReconciliationService reconciliationService;

    @SuppressWarnings("removal")
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
    void testCreateReconciliation() throws Exception {
        ReconciliationRequest request = new ReconciliationRequest(1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now());
        ReconciliationResponse response = new ReconciliationResponse(1L, 1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now());

        when(reconciliationService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/reconciliations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReconciliation() throws Exception {
        when(reconciliationService.getById(1L)).thenReturn(new ReconciliationResponse(1L, 1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now()));

        mockMvc.perform(get("/api/reconciliations/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllReconciliations() throws Exception {
        when(reconciliationService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/reconciliations"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateReconciliation() throws Exception {
        ReconciliationRequest request = new ReconciliationRequest(1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now());

        when(reconciliationService.update(eq(1L), any())).thenReturn(new ReconciliationResponse(1L, 1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now()));

        mockMvc.perform(put("/api/reconciliations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReconciliation() throws Exception {
        mockMvc.perform(delete("/api/reconciliations/1"))
                .andExpect(status().isOk());
    }
}
