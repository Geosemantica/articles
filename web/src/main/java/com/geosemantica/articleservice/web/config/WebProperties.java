package com.geosemantica.articleservice.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.geosemantica.articleservice.web")
@Getter
@Setter
public class WebProperties {
    private String accessTokenHeader;
    private boolean includeTraceError;
}
