package org.rossijr.authentication.service;

import org.rossijr.authentication.model.Role;
import org.rossijr.authentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Role entity
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
     * Save role
     *
     * @param role role to be saved
     * @return saved role
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
