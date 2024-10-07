package com.geosemantica.articleservice.web.config.annotations;


import com.geosemantica.articleservice.facades.model.enums.Role;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AllowFor {
    Role[] value();
}
