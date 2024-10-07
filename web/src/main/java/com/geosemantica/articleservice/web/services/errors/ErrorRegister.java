package com.geosemantica.articleservice.web.services.errors;

import com.geosemantica.articleservice.facades.exceptions.EntityNotFoundException;
import com.geosemantica.articleservice.facades.exceptions.InsufficientAuthorityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import com.geosemantica.articleservice.web.model.ErrorBody;

import java.util.HashMap;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class ErrorRegister {
    private final ErrorHandlersMap<Exception, ErrorBody> errorHandlers = new ErrorHandlersMap<>();
    private final ErrorBodyService errorBodyService;

    @PostConstruct
    protected void init() {
        register(EntityNotFoundException.class,
                (e) -> errorBodyService.createSimpleErrorBody("Entity " + e.getEntityClass().getSimpleName() + " with identity " + e.getId() + " not found.", e, HttpStatus.NOT_FOUND));
        register(InsufficientAuthorityException.class,
                (e) -> errorBodyService.createSimpleErrorBody(e.getMessage(), e, HttpStatus.FORBIDDEN));
        register(NoResourceFoundException.class, //Cause: not found an endpoint for the specified url. Since spring 3.2.0 Exception handler is required to process this exception.
                (e) -> errorBodyService.createSimpleErrorBody("Path " + e.getResourcePath() + " not found.", e, HttpStatus.NOT_FOUND));
    }

    public ErrorBody lookup(Exception e) {
        return errorHandlers.getBody(e);
    }

    protected <T extends Exception> void register(Class<T> exceptionClass, Function<T, ErrorBody> converter) {
        errorHandlers.put(exceptionClass, (Function<Exception, ErrorBody>) converter);
    }

    private static class ErrorHandlersMap<E extends Exception, R extends ErrorBody> extends HashMap<Class<? extends E>, Function<E, R>> {
        R getBody(E exception) {
            Function<E, R> f = get(exception.getClass());
            if (f == null) {
                return null;
            }
            return f.apply(exception);
        }
    }
}
