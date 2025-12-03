package com.example.skillnest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_KEY_EXCEPTION(1001, "Uncategorized Exception", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 4 to 50 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED_EXCEPTION(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_EXCEPTION(1006, "You do not have permission!", HttpStatus.FORBIDDEN),
    PRODUCT_NOT_FOUND(1007, "Product not found!", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS(1008, "Product already exists!", HttpStatus.CONFLICT),
    //    UNCATEGORIZED_EXCEPTION(1008, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_EMAIL(1009, "Please provide a valid email address!", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1011, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_HAS_ENROLLED(1012, "User has enrolled this course", HttpStatus.CONFLICT),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
