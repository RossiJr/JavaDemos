package org.rossijr.projecttemplate.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CreateUserResponseDTO {
    private final UUID id;
    private final String email;
    private final ZonedDateTime createdAt;

    public CreateUserResponseDTO(UUID id, String email, ZonedDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
