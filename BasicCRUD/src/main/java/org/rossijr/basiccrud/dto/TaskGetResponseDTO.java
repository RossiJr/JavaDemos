package org.rossijr.basiccrud.dto;

import java.time.ZonedDateTime;

public class TaskGetResponseDTO {
    private final Long id;
    private final String title;
    private final String description;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final ZonedDateTime dueDate;
    private final String priority;
    private final String status;

    public TaskGetResponseDTO(Long id, String title, String description, ZonedDateTime createdAt, ZonedDateTime updatedAt, ZonedDateTime dueDate, String priority, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}
