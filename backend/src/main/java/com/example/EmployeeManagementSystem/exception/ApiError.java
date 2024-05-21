package com.example.EmployeeManagementSystem.exception;

import java.time.LocalDateTime;

public class ApiError {
    private String path;
    private String message;
    private int statusCode;
    private LocalDateTime localDateTime;

    public ApiError(String path, String message, int statusCode, LocalDateTime localDateTime) {
        this.path = path;
        this.message = message;
        this.statusCode = statusCode;
        this.localDateTime = localDateTime;
    }

    // Getters and Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
