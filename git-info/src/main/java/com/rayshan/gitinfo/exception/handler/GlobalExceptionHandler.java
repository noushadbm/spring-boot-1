package com.rayshan.gitinfo.exception.handler;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.StackSize;
import com.rayshan.gitinfo.exception.ErrorCodes;
import com.rayshan.gitinfo.exception.ErrorResponse;
import java.util.stream.Collectors;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        logger.atSevere().withStackTrace(StackSize.FULL).withCause(ex).log(
                "MethodArgumentNotValidException thrown %s", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.addAll(
                ex.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        ErrorCodes.INVALID_REQUEST.getErrorData(
                                                fieldError.getField(),
                                                fieldError.getDefaultMessage()))
                        .collect(Collectors.toList()));

        // headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }
}
