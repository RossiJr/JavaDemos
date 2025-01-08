package org.rossijr.authentication.service;

import org.rossijr.authentication.model.Role;
import org.rossijr.authentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return roleRepository.findByName(name);
    }
}
