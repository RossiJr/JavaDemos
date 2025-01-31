package org.rossijr.authentication.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

/**
 * Represents the association between a role and a permission in the authentication system.
 *
 * <p>This entity maps to the <b>tb_mm_role_permission</b> table and is used to define
 * the many-to-many relationship between {@link Role} and {@link Permission} entities.
 * Each instance of this class associates a role with a specific permission.</p>
 *
 * <h3>Database Mapping:</h3>
 * <ul>
 *   <li><b>Table Name:</b> tb_mm_role_permission</li>
 *   <li><b>Primary Key:</b> id (auto-generated)</li>
 *   <li><b>Foreign Keys:</b>
 *     <ul>
 *       <li>role_id: References the {@link Role} entity</li>
 *       <li>permission_id: References the {@link Permission} entity</li>
 *       <li>assigned_by: References the {@link User} entity</li>
 *     </ul>
 *       <li>assigned_at: Timestamp indicating when the role was assigned to the user.</li>
 *   </li>
 * </ul>
 *
 * @see Role
 * @see Permission
 */
@Entity
@Table(name = "tb_mm_role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "assigned_by", nullable = false)
    private User assignedBy;

    @Column(name = "assigned_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime assignedAt;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }

    public RolePermission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public ZonedDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(ZonedDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}

