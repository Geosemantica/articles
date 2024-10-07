package com.geosemantica.articleservice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            log.info("Deployment");
            final File file = new File("started");
            if (!file.createNewFile()) {
                log.info("Start file creating failed - already exists.");
            }
            else {
                log.info("Start file created at: {}", file.getAbsolutePath());
            }
        }
        catch (Throwable e) {
            log.error("Start file creating failed:", e);
        }
    }
}
