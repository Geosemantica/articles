package com.geosemantica.articleservice.web.services.errors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.geosemantica.articleservice.web.config.WebProperties;
import com.geosemantica.articleservice.web.model.ErrorBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
@Component
public class ErrorBodyService {
    private final WebProperties properties;

    public ErrorBody createSimpleErrorBody(String message, Exception exception, HttpStatus httpStatus) {
        String trace = null;
        if (properties.isIncludeTraceError()) {
            trace = getExceptionTrace(exception);
        }
        return new ErrorBody(message, trace, httpStatus);
    }

    private String getExceptionTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
