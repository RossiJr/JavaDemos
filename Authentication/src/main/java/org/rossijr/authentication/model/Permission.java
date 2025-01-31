package org.rossijr.authentication.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Represents a tre Permission (authority) that a user can have.
 *
 * <p>Permissions are used to control access to the application's endpoints and resources.
 * They are associated with roles, which are then assigned to users.</p>
 * <p>In this demo application, permissions are the ones used to define the accesses, and roles are used only to group permissions.</p>
 *
 * <h3>Database Mapping:</h3>
 * <ul>
 *   <li>Maps to the table <b>tb_permission</b> in the database.</li>
 *   <li>Each permission has an unique ID, name, and description.</li>
 * </ul>
 *
 * <h3>Spring Security Integration:</h3>
 *  <ul>
 *    <li>Implements {@link GrantedAuthority} to define the authority granted by this role.</li>
 *    <li>Method {@code getAuthority()} returns the role name, which integrates with Spring Security's authorization system.</li>
 *  </ul>
 */
@Entity
@Table(name = "tb_permission")
public class Permission implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    private String description;

    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
