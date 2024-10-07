package com.geosemantica.articleservice.service;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource("classpath:service.properties")
@ConfigurationPropertiesScan
@AutoConfiguration
@ComponentScan
public class ServiceModule {
}
