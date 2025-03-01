package org.rossijr.basiccrud.dto;

import jakarta.validation.constraints.*;
import org.rossijr.basiccrud.enums.Priority;

import java.time.ZonedDateTime;

/**
 * DTO responsible for receiving the request of creating a new Task, exposing some examples of validators from the Spring Boot ecosystem
 */
public class TaskCreateRequestDTO {
    /**
     * <ul>
     *     <li>The annotation {@code @NotBlank} indicates that the field must not be blank.</li>
     *     <li>The annotation {@code @Size(max = 100)} specifies the maximum size of the field.</li>
     * </ul>
     */
    @NotBlank
    @Size(max = 100)
    private final String title;

    /**
     * <ul>
     *     <li>The annotation {@code @Size(max = 255)} specifies the maximum size of the field.</li>
     * </ul>
     */
    @Size(max = 255)
    private final String description;

    /**
     * <ul>
     *     <li>The annotation {@code @FutureOrPresent} indicates that the date must be in the future or present.</li>
     *     <li>Alternatively, the annotation {@code @Future} indicates that the date must be in the future.</li>
     *     <li>Or the annotation {@code @Past} indicates that the date must be in the past.</li>
     * </ul>
     */
    @FutureOrPresent
    private final ZonedDateTime dueDate;

    /**
     * <ul>
     *     <li>The annotation {@code @Min(1)} indicates that the field must be greater than or equal to 1.</li>
     * </ul>
     */
    @Min(1)
    @NotNull
    private final Long projectId;

    /**
     * <ul>
     *     <li>The annotation {@code @NotNull} indicates that the field must not be null.</li>
     * </ul>
     */
    @NotNull
    private final Priority priority;

    public TaskCreateRequestDTO(String title, String description, ZonedDateTime dueDate, Long projectId, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.priority = priority;
    }

    public @NotBlank @Size(max = 100) String getTitle() {
        return title;
    }

    public @Size(max = 255) String getDescription() {
        return description;
    }

    public @FutureOrPresent ZonedDateTime getDueDate() {
        return dueDate;
    }

    public @Min(0) Long getProjectId() {
        return projectId;
    }

    public @NotNull Priority getPriority() {
        return priority;
    }
}
