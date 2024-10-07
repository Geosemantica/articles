package com.geosemantica.articleservice.brokerfacades.model.messages;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserEventMessage {
    @NotNull
    private UUID userId;
}
