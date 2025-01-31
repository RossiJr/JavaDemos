package org.rossijr.authentication.service;

import org.rossijr.authentication.model.Permission;
import org.rossijr.authentication.model.Role;
import org.rossijr.authentication.model.RolePermission;
import org.rossijr.authentication.model.User;
import org.rossijr.authentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Service for Role entity
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionService permissionService,
                       RolePermissionService rolePermissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }

    /**
     * Get role by name
     *
     * @param name role name
     * @return role
     */
    public Role getRoleByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name is required");
        }
        return roleRepository.findByName(name);
    }

    /**
     * Assign permission to role
     * @param roleId role that is going to receive the permission
     * @param permissionId permission that is going to be assigned to the role
     * @param userId user that is assigning the permission
     */
    public void assignPermissionToRole(Long roleId, Long permissionId, UUID userId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionService.findById(permissionId);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermission.setAssignedAt(ZonedDateTime.now());
        rolePermission.setAssignedBy(new User(userId));

        rolePermissionService.save(rolePermission);
    }

    /**
     * Save role
     * @param role role to be saved
     * @return saved role
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
