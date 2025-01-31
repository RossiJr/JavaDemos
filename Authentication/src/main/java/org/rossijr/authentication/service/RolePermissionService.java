package org.rossijr.authentication.service;

import org.rossijr.authentication.model.RolePermission;
import org.rossijr.authentication.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for RolePermission entity
 *
 * <p>It is not fully implemented yet, for example it is missing logs, exception handling, input validation, etc.</p>
 *
 * <p>Due to this reason and the fact that what is done here is intuitive, no further comments will be provided</p>
 */
@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public RolePermission save(RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

}