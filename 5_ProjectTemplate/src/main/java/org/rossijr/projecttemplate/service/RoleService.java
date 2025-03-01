package org.rossijr.projecttemplate.service;

import org.rossijr.projecttemplate.model.Role;
import org.rossijr.projecttemplate.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role getRoleByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name is required");
        }
        return roleRepository.findByName(name);
    }
}
