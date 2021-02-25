package com.rayshan.gitinfo.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReasonCode {
    BAD_REQUEST("BadRequest"),
    METHOD_NOT_ALLOWED("Method Not allowed"),
    INVALID_PARAMETER("InvalidParameter"),
    INVALID_QUERY("InvalidQuery"),
    INVALID_HEADER("InvalidHeader"),
    PARSE_ERROR("ParseError"),
    TOO_MANY_PARTS("TooManyParts"),
    WRONG_URL_FOR_UPLOAD("WrongUrlForUpload"),
    NOT_MODIFIED("NotModified"),
    UNAUTHORIZED("Unauthorized"),
    AUTH_ERROR("AuthError"),
    EXPIRED("Expired"),
    REQUIRED("Required"),
    RESOURCE_UNAVAILABLE("ResourceUnavailable"),
    SERVICE_UNAVAILABLE("ServiceUnavailable"),
    INVALID("Invalid"),
    INTERNAL_SERVER_ERROR("InternalServerError"),
    SWAGGER_CONTRACT_VALIDATION("SwaggerContractValidation");

    @JsonValue private final String reasonCode;
}
