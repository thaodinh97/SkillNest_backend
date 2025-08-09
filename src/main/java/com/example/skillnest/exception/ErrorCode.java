package com.example.skillnest.exception;

public enum ErrorCode {
    INVALID_KEY_EXCEPTION(1001, "Uncategorized Exception"),
    USER_EXISTED(1002, "User existed!"),
    PASSWORD_INVALID(1003, "Password must be at least 4 to 50 characters"),
    USER_NOT_FOUND(1004, "User not found!"),
    UNAUTHENTICATED_EXCEPTION(1005, "Unauthenticated Exception"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
