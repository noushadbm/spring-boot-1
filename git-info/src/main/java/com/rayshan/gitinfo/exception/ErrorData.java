package com.rayshan.gitinfo.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorData implements Serializable {

    @JsonIgnore private HttpStatus httpStatus;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("ReasonCode")
    private ReasonCode reasonCode;

    @JsonProperty("Message")
    private String message;
}
