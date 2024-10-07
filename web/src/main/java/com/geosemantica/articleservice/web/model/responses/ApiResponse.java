package com.geosemantica.articleservice.web.model.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.geosemantica.articleservice.web.model.ErrorBody;

import java.util.Collections;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private List<ErrorBody> errors;
    private ErrorBody error;

    private String message;
    private List<String> trace;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ErrorBody error) {
        this.message = error.getMessage();
        if (error.getTrace() != null) {
            this.trace = Collections.singletonList(error.getTrace());
        }
    }

    public ApiResponse(List<ErrorBody> errors) {
        this.errors = errors;
    }

    public ApiResponse(ErrorBody error, T data) {
        this.errors = Collections.singletonList(error);
        this.data = data;
    }

    public static ApiResponse error(final ErrorBody errorBody) {
        return new ApiResponse(errorBody);
    }

    public static ApiResponse error(final List<ErrorBody> errorBodies) {
        return new ApiResponse(errorBodies);
    }

    public static ApiResponse error(final ErrorBody errorBody, final Object data) {
        return new ApiResponse(errorBody, data);
    }


    public static <T> ResponseEntity<ApiResponse<T>> failure(final T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data);
        return ResponseEntity.ok(apiResponse);
    }

    public static <T> ResponseEntity<T> success(final T data) {
        return ResponseEntity.ok(data);
    }

    public static <T> ResponseEntity<T> created(final T data) {
        return new ResponseEntity<T>(data, new HttpHeaders(), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success() {
        return ResponseEntity.ok(new ApiResponse<T>((T) null));
    }

}

