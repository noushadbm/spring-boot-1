package com.rayshan.gitinfo.exception;

import java.text.MessageFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCodes {
    INVALID_REQUEST(
            "GIT_HUB_API_ERR_400_01",
            ReasonCode.BAD_REQUEST,
            "InvalidParameter : {0} {1}",
            HttpStatus.BAD_REQUEST);

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
    private final ReasonCode reasonCode;

    ErrorCodes(String code, ReasonCode reasonCode, String description, HttpStatus httpStatus) {
        this.code = code;
        this.reasonCode = reasonCode;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public ErrorData getErrorData(Object... v) {
        return new ErrorData(this.httpStatus, this.code, this.reasonCode, this.getDescription(v));
    }

    public String getDescription(Object... v) {
        return MessageFormat.format(description, v);
    }
}
