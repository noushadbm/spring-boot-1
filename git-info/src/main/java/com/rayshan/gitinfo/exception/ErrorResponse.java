package com.rayshan.gitinfo.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @JsonProperty("Errors")
    private List<ErrorData> errors;

    public ErrorResponse() {
        errors = new ArrayList<>();
    }

    public void add(ErrorData error) {
        errors.add(error);
    }

    public ErrorResponse addAll(List<ErrorData> errorDataList) {
        this.errors.addAll(errorDataList);
        return this;
    }
}
