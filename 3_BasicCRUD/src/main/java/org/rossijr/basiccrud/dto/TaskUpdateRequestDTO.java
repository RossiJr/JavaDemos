package org.rossijr.basiccrud.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.rossijr.basiccrud.enums.Priority;
import org.rossijr.basiccrud.enums.Status;

import java.time.ZonedDateTime;

/**
 * DTO responsible for receiving the request of creating a new Task, exposing some examples of validators from the Spring Boot ecosystem
 */
public class TaskUpdateRequestDTO {
    /**
     * <ul>
     *     <li>The annotation {@code @NotNull} indicates that the field must not be null.</li>
     *     <li>The annotation {@code @Min(0)} specifies that the field must be greater than or equal to 0.</li>
     * </ul>
     */
    @NotNull
    @Min(0)
    private final Long id;

    /**
     * <ul>
     *     <li>The annotation {@code @NotBlank} indicates that the field must not be blank.</li>
     *     <li>The annotation {@code @Size(max = 100)} specifies the maximum size of the field.</li>
     * </ul>
     */
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
    private final Long projectId;

    private final Priority priority;

    private final Status status;

    public TaskUpdateRequestDTO(Long id, String title, String description, ZonedDateTime dueDate, Long projectId, Priority priority, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.priority = priority;
        this.status = status;
    }

    public @NotNull @Min(0) Long getId() {
        return id;
    }

    public @Size(max = 100) String getTitle() {
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

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }
}
