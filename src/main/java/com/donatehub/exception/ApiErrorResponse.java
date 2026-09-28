package com.donatehub.exception;

public class ApiErrorResponse {
    private final boolean success;
    private final String message;
    private final int status;

    public ApiErrorResponse(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
