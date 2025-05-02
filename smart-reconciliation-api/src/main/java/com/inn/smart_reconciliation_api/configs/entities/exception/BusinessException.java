package com.inn.smart_reconciliation_api.configs.entities.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
