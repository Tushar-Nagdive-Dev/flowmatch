package com.inn.smart_reconciliation_api.services;

import com.inn.smart_reconciliation_api.dtos.LoginRequest;
import com.inn.smart_reconciliation_api.dtos.LoginResponse;
import com.inn.smart_reconciliation_api.dtos.RegisterRequest;

public interface AuthService {
    
    public void register(RegisterRequest request);

    public LoginResponse login(LoginRequest request);
}
