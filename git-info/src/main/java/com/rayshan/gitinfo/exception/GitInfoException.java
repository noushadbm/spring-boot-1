package com.rayshan.gitinfo.exception;

import org.springframework.http.HttpStatus;

public class GitInfoException extends Exception {
    private final ErrorData errorData;

    public GitInfoException(ErrorData errorData) {
        super(errorData.getMessage());
        this.errorData = errorData;
    }

    public GitInfoException(HttpStatus httpStatus, String message) {
        super(message);
        this.errorData = new ErrorData(httpStatus, message);
    }

    public ErrorData getErrorData() {
        return errorData;
    }
}
