package org.rossijr.authentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Represents the association between a user and a role in the authentication system.
 *
 * <p>This entity maps to the <b>tb_mm_user_role</b> table and is used to define
 * the many-to-many relationship between {@link User} and {@link Role} entities.
 * Each instance of this class associates a user with a specific role.</p>
 *
 * <h3>Database Mapping:</h3>
 * <ul>
 *   <li><b>Table Name:</b> tb_mm_user_role</li>
 *   <li><b>Primary Key:</b> id (auto-generated)</li>
 *   <li><b>Foreign Keys:</b>
 *     <ul>
 *       <li>user_id: References the {@link User} entity</li>
 *       <li>role_id: References the {@link Role} entity</li>
 *     </ul>
 *   </li>
 *   <li><b>Assigned At:</b> Timestamp indicating when the role was assigned to the user.</li>
 * </ul>
 *
 * @see User
 * @see Role
 */
@Entity
@Table(name = "tb_mm_user_role")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "assigned_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime assignedAt;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.assignedAt = ZonedDateTime.now();
    }

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ZonedDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(ZonedDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
