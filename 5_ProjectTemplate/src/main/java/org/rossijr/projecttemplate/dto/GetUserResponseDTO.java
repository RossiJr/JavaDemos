package org.rossijr.projecttemplate.dto;

import org.rossijr.projecttemplate.model.Role;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

public class GetUserResponseDTO {
    private final UUID id;
    private final String email;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final Set<Role> roles;

    public GetUserResponseDTO(UUID id, String email, ZonedDateTime createdAt, ZonedDateTime updatedAt, Set<Role> roles) {
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

    public Set<Role> getRoles() {
        return roles;
    }
}
