package com.geosemantica.articleservice.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.geosemantica.articleservice.facades.model.CommentFacade;
import com.geosemantica.articleservice.facades.model.enums.Role;
import com.geosemantica.articleservice.web.model.security.AnonymousPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import com.geosemantica.articleservice.facades.model.ArticleFacade;
import com.geosemantica.articleservice.web.config.WebProperties;
import com.geosemantica.articleservice.web.mixins.ArticleMixin;
import com.geosemantica.articleservice.web.mixins.CommentMixin;
import com.geosemantica.articleservice.web.model.Views;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@PropertySource("classpath:web.properties")
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@EnableWebSecurity
@ComponentScan
@Configuration
public class WebModule {
    private final WebProperties properties;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer mapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.mixIn(ArticleFacade.class, ArticleMixin.class);
            jacksonObjectMapperBuilder.mixIn(CommentFacade.class, CommentMixin.class);

            jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
            jacksonObjectMapperBuilder.dateFormat(dateFormat());
            jacksonObjectMapperBuilder.defaultViewInclusion(true);
        };
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        SerializationConfig serializationConfig = objectMapper
                .getSerializationConfig().withView(Views.DefaultView.class);
        objectMapper.setConfig(serializationConfig);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        return objectMapper;
    }

    @Bean
    public SimpleDateFormat dateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    }

    @Bean
    public HeaderBearerTokenResolver bearerTokenResolver() {
        return new HeaderBearerTokenResolver(properties.getAccessTokenHeader());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers(headers -> {
                    headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable);
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    headers.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable);
                    headers.xssProtection(HeadersConfigurer.XXssConfig::disable);
                })
                .anonymous(configurer -> configurer.authorities(Role.ANONYMOUS).principal(new AnonymousPrincipal()))
                .portMapper(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(configurer ->
                        configurer.authenticationManagerResolver(context ->
                                http.getSharedObject(AuthenticationManager.class)))
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

}
