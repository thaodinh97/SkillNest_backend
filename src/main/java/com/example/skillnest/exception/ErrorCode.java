package com.example.skillnest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter

public enum ErrorCode {

    INVALID_KEY_EXCEPTION(1001, "Uncategorized Exception", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 4 to 50 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED_EXCEPTION(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_EXCEPTION(1006, "You do not have permission!", HttpStatus.FORBIDDEN),
    PRODUCT_NOT_FOUND(1007, "Product not found!",  HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(1008, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode)
    {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
