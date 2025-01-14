package org.rossijr.projecttemplate.dto;

import org.rossijr.projecttemplate.model.UserRole;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

public class GetUserResponseDTO {
    private final UUID id;
    private final String email;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final Set<UserRole> roles;

    public GetUserResponseDTO(UUID id, String email, ZonedDateTime createdAt, ZonedDateTime updatedAt, Set<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
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

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}
