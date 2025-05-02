package com.inn.smart_reconciliation_api.configs.entities.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}