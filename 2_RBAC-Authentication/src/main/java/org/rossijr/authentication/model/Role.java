package org.rossijr.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a role entity in the authentication system.
 *
 * <p>This class maps to the <b>tb_role</b> table in the database and is used to define
 * roles within the application. Roles determine the authorities to a user and are associated
 * with users through the {@link UserRole} entity.</p>
 *
 * <h3>Database Mapping:</h3>
 * <ul>
 *   <li><b>Table Name:</b> tb_role</li>
 *   <li><b>Primary Key:</b> id (auto-generated)</li>
 *   <li><b>Fields:</b>
 *     <ul>
 *       <li><b>name:</b> Unique name of the role (e.g., ROLE_ADMIN, ROLE_USER).</li>
 *       <li><b>description:</b> Optional description of the role's purpose.</li>
 *       <li><b>createdAt:</b> Timestamp indicating when the role was created.</li>
 *       <li><b>updatedAt:</b> Timestamp indicating when the role was last updated.</li>
 *       <li><b>createdBy:</b> User who created the role.</li>
 *     </ul>
 *   </li>
 *   <li><b>Relationships:</b>
 *     <ul>
 *       <li><b>userRoles:</b> Many-to-many association with {@link User} through {@link UserRole}.</li>
 *       <li><b>createdBy:</b> Many-to-one association with {@link User} indicating the user who created the role.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @see UserRole
 */
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt;

    @JsonIgnore
    @Column(name = "udpated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
