package com.example.backend.error;

public class ApiUsageLimit extends RuntimeException {
    public ApiUsageLimit(String message) {
        super(message);
    }
}
