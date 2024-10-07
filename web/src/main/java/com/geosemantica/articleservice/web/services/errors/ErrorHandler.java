package com.geosemantica.articleservice.web.services.errors;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.geosemantica.articleservice.facades.exceptions.BaseException;
import com.geosemantica.articleservice.web.model.ErrorBody;
import com.geosemantica.articleservice.web.model.responses.ApiResponse;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final ErrorRegister errorRegister;
    private final ErrorBodyService errorBodyService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        StringBuilder message = new StringBuilder();
        boolean isFirst = true;
        for (Object error : ex.getBindingResult().getAllErrors()) {
            if (!isFirst) {
                message.append("\n");
            }
            isFirst = false;
            if (error instanceof FieldError fieldError) {
                message.append("Field '")
                        .append(fieldError.getField())
                        .append("' has wrong value '")
                        .append(fieldError.getRejectedValue())
                        .append("' because ")
                        .append(fieldError.getDefaultMessage());
            }
            else if (error instanceof ObjectError objectError) {
                message.append(objectError.getDefaultMessage());
            }
            else {
                log.warn("Unsupported MethodArgumentNotValid inner error class: {}.", error.getClass());
                message.append("Unsupported error");
            }
        }
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(message.toString(), ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        String message;
        if (ex.getRequiredType() != null) {
            message = "Request param has wrong value '" + ex.getValue() + "'." +
                    " Value must be of type " + ex.getRequiredType().getSimpleName();
        }
        else {
            message = "Request param has wrong value '" + ex.getValue() + "'.";
        }
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(message, ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, headers, statusCode, request);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException(MultipartException ex, WebRequest request) {
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody("Title with name 'file' not specified in body request.", ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, null, errorBody.getHttpStatus(), request);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
        final ErrorBody errorBody = createErrorBody(ex);
        return handleExceptionInternal(ex, errorBody, null, errorBody.getHttpStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorBody errorBody = errorBodyService.createSimpleErrorBody(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorBody, null, errorBody.getHttpStatus(), request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (log.isDebugEnabled()) {
            log.warn("Handled exception occurs", ex);
        }
        else {
            // to avoid fill our log with traces
            log.warn("Handled exception occurs, {}: {}", ex.getClass(), ex.getMessage());
        }
        if (body == null) {
            ErrorBody errorBody = createErrorBody(ex);
            statusCode = errorBody.getHttpStatus();
            body = errorBody;
        }
        ApiResponse apiResponse = ApiResponse.error((ErrorBody) body);
        return super.handleExceptionInternal(ex, apiResponse, headers, statusCode, request);
    }

    private ErrorBody createErrorBody(Exception ex) {
        ErrorBody errorBody = errorRegister.lookup(ex);
        if (errorBody == null) {
            log.error("There is no error body for exception.", ex);
            errorBody = errorBodyService.createSimpleErrorBody("Unhandled exception occurs.", ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return errorBody;
    }
}
