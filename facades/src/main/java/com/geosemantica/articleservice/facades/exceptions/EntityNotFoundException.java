package com.geosemantica.articleservice.facades.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EntityNotFoundException extends BaseException {
    private final Class<?> entityClass;
    private final Object id;

    protected EntityNotFoundException(String message, Object id, Class<?> entityClass) {
        super(message);
        this.entityClass = entityClass;
        this.id = id;
    }

    public EntityNotFoundException(UUID uuid, Class<?> entityClass) {
        this("There is not found entity " + entityClass + " with uuid " + uuid, uuid, entityClass);
    }

    public EntityNotFoundException(Long id, Class<?> entityClass) {
        this("There is not found entity " + entityClass + " with id " + id, id, entityClass);
    }
}
