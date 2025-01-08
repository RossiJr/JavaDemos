package org.rossijr.authentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a role entity in the authentication system.
 *
 * <p>This class maps to the <b>tb_role</b> table in the database and is used to define
 * roles within the application. Roles determine the authorities or permissions
 * granted to a user and are associated with users through the {@link UserRole} entity.</p>
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
 *     </ul>
 *   </li>
 *   <li><b>Relationships:</b>
 *     <ul>
 *       <li><b>userRoles:</b> Many-to-many association with {@link User} through {@link UserRole}.</li>
 *     </ul>
 *   </li>
 * </ul>
 * <h3>Spring Security Integration:</h3>
 * <ul>
 *   <li>Implements {@link GrantedAuthority} to define the authority granted by this role.</li>
 *   <li>Method {@code getAuthority()} returns the role name, which integrates with Spring Security's authorization system.</li>
 * </ul>
 *
 * @see UserRole
 * @see GrantedAuthority
 **/
@Entity
@Table(name = "tb_role")
public class Role implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
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
}
