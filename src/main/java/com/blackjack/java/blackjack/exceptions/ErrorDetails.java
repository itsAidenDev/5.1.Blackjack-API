package com.blackjack.java.blackjack.exceptions;

public final class ErrorDetails {
    private final int status;
    private final String error;
    private final String message;

    public ErrorDetails(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
