package com.geosemantica.articleservice.messagebroker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.geosemantica.articleservice.message-broker.publishing")
@Getter
@Setter
public class PublishingProperties {
    private String commentsExchange;
    private String articlesExchange;

    private String commentsRoutingKey;
    private String articlesRoutingKey;
}
