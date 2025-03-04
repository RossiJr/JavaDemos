package org.rossijr.basiccrud.model;

import jakarta.persistence.*;
import org.rossijr.basiccrud.enums.Priority;
import org.rossijr.basiccrud.enums.Status;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Represents a task in the system.
 * <ul>
 * <li>The annotation {@code @Entity} indicates that this class is a JPA entity (which means it will be mapped to a database table).</li>
 * <li>The annotation {@code @Table} specifies the name of the table in the database where the entity will be stored.</li>
 * </ul>
 */
@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
    /**
     * ID as a unique identifier for the task.
     * <ul>
     * <li>{@code @Id}: Indicates that this field is the primary key of the entity.</li>
     * <li>{@code @GeneratedValue(strategy = GenerationType.IDENTITY)}: Specifies that the ID will be automatically generated by the database in a sequential manner.</li>
     * </ul>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * <ul>
     * <li>{@code @Column(nullable = false, length = 100, name = "TITLE")}:</li>
     * <ul>
     * <li>{@code nullable = false}: Ensures this field cannot be null in the database.</li>
     * <li>{@code length = 100}: Limits the maximum length of the field to 100 characters.</li>
     * <li>{@code name = "TITLE"}: Specifies the name of the column in the database table as "TITLE".</li>
     * </ul>
     * </ul>
     */
    @Column(nullable = false, length = 100, name = "TITLE")
    private String title;

    /**
     * <ul>
     * <li>{@code @Column(length = 255, name = "DESCRIPTION")}:</li>
     * <ul>
     * <li>{@code length = 255}: Limits the maximum length of the field to 255 characters.</li>
     * <li>{@code name = "DESCRIPTION"}: Specifies the name of the column in the database table as "DESCRIPTION".</li>
     * </ul>
     * </ul>
     */
    @Column(length = 255, name = "DESCRIPTION")
    private String description;

    /**
     * <ul>
     * <li>{@code @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "CREATED_AT")}:</li>
     * <ul>
     * <li>{@code columnDefinition = "TIMESTAMP WITH TIME ZONE"}: Specifies that the column in the database table will store the date and time along with the time zone.</li>
     * <li>{@code name = "CREATED_AT"}: Specifies the name of the column in the database table as "CREATED_AT".</li>
     * </ul>
     * </ul>
     *
     * <p>Consider the best option for your needs, as the {@link ZonedDateTime} demands more space in the database compared to options that do not store the time zone.</p>
     */
    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt;

    /**
     * <ul>
     * <li>{@code @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "UPDATED_AT")}:</li>
     * <ul>
     * <li>{@code columnDefinition = "TIMESTAMP WITH TIME ZONE"}: Specifies that the column in the database table will store the date and time along with the time zone.</li>
     * <li>{@code name = "UPDATED_AT"}: Specifies the name of the column in the database table as "UPDATED_AT".</li>
     * </ul>
     * </ul>
     *
     * <p>Consider the best option for your needs, as the {@link ZonedDateTime} demands more space in the database compared to options that do not store the time zone.</p>
     */
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updatedAt;

    /**
     * <ul>
     * <li>{@code @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "DUE_DATE")}:</li>
     * <ul>
     * <li>{@code columnDefinition = "TIMESTAMP WITH TIME ZONE"}: Specifies that the column in the database table will store the date and time along with the time zone.</li>
     * <li>{@code name = "DUE_DATE"}: Specifies the name of the column in the database table as "DUE_DATE".</li>
     * </ul>
     * </ul>
     *
     * <p>Consider the best option for your needs, as the {@link ZonedDateTime} demands more space in the database compared to options that do not store the time zone.</p>
     */
    @Column(name = "DUE_DATE", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime dueDate;

    /**
     * <ul>
     * <li>{@code @Enumerated(EnumType.STRING)}: Specifies that the enum values will be stored as strings in the database.
     * (This is the <b>recommended approach</b> to avoid issues when changing the order of the enum values. But if you need to store the enum values as integers,
     * you can use {@code @Enumerated(EnumType.ORDINAL)} instead and it will store the values in the order they are declared in the enum class.)</li>
     * <li>{@code @Column(name = "PRIORITY")}: Sets the column name to "PRIORITY" in the database.</li>
     * </ul>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY")
    private Priority priority;

    /**
     * <ul>
     * <li>{@code @Enumerated(EnumType.STRING)}: Specifies that the enum values will be stored as strings in the database.</li>
     * <li>{@code @Column(name = "STATUS")}: Sets the column name to "STATUS" in the database.</li>
     * </ul>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    /**
     * The project to which the task belongs.
     * <ul>
     * <li>{@code @ManyToOne}: Specifies a many-to-one relationship with the {@link Project} entity.</li>
     * <li>{@code @JoinColumn(name = "project_id")}: Maps this relationship to a column named "project_id" in the database, which is basically the foreign key.</li>
     * </ul>
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    public Task() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
