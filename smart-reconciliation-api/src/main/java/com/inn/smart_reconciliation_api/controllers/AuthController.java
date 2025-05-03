package com.inn.smart_reconciliation_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.LoginRequest;
import com.inn.smart_reconciliation_api.dtos.LoginResponse;
import com.inn.smart_reconciliation_api.dtos.RegisterRequest;
import com.inn.smart_reconciliation_api.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    private final AuditTrailService auditTrailService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {

        // Audit Log (optional but professional)
        if (userDetails != null) {
            String username = userDetails.getUsername();
            Long userId = jwtUtil.getLoggedInUserId();

            auditTrailService.recordAction(AuditEntityType.AUTH, userId, AuditAction.LOGOUT, userId, username);
        }
        // Just return success → Frontend will clear the token
        return ResponseEntity.ok("User logged out successfully");
    }
}
