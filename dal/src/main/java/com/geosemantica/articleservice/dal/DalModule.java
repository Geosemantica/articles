package com.geosemantica.articleservice.dal;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource("classpath:dal.properties")
@ConfigurationPropertiesScan
@EnableTransactionManagement
@EnableJpaRepositories
@AutoConfiguration
@ComponentScan
@EntityScan
public class DalModule {

}
