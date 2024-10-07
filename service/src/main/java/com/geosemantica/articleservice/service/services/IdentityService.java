package com.geosemantica.articleservice.service.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentityService {

    public UUID newUuid() {
        return UUID.randomUUID();
    }
}
