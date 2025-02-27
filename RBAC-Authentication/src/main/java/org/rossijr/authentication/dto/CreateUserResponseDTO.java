package org.rossijr.authentication.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO for the response of the create user endpoint.
 */
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
