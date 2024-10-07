package com.geosemantica.articleservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorBody {

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String trace;

    @JsonIgnore
    private final HttpStatus httpStatus;

    public ErrorBody(String message, String trace, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.trace = trace;
    }
}
