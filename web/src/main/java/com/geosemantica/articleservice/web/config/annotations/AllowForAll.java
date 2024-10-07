package com.geosemantica.articleservice.web.config.annotations;


import com.geosemantica.articleservice.facades.model.enums.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AllowFor({
        Role.ROLE_ANONYMOUS,
        Role.ROLE_USER,
        Role.ROLE_MODERATOR,
        Role.ROLE_ADMIN
})
public @interface AllowForAll {
}
