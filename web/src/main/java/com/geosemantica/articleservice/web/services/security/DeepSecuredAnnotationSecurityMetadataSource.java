package com.geosemantica.articleservice.web.services.security;

import com.geosemantica.articleservice.web.config.annotations.AllowFor;
import com.geosemantica.articleservice.web.model.security.RoleConfigAttribute;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeepSecuredAnnotationSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {
    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        return walkThrough(method, targetClass);
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return walkThrough(clazz, null);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private Collection<ConfigAttribute> walkThrough(AnnotatedElement firstTarget, AnnotatedElement secondOptionalTarget) {
        List<AllowFor> allows = new ArrayList<>();
        Set<AnnotatedElement> walked = Collections.newSetFromMap(new IdentityHashMap<>());
        walkThrough(firstTarget, allows, walked);
        if (allows.isEmpty()) {
            return null;
        }
        return allows
                .stream()
                .map(AllowFor::value)
                .flatMap(Stream::of)
                .map(RoleConfigAttribute::new)
                .collect(Collectors.toList());

    }

    private void walkThrough(AnnotatedElement classOrMethod, List<AllowFor> allows, Set<AnnotatedElement> walked) {
        for (Annotation annotation : classOrMethod.getDeclaredAnnotations()) {
            if (annotation instanceof AllowFor) {
                allows.add((AllowFor) annotation);
                continue;
            }
            if (walked.contains(annotation.annotationType())) {
                continue;
            }
            walked.add(annotation.annotationType());
            walkThrough(annotation.annotationType(), allows, walked);
        }
    }
}

