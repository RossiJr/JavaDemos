package org.rossijr.authentication.service;

import org.rossijr.authentication.exception.ObjectNotFoundException;
import org.rossijr.authentication.model.Permission;
import org.rossijr.authentication.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Permission entity
 *
 * <p>It is not fully implemented yet, for example it is missing logs, exception handling, input validation, etc.</p>
 *
 * <p>Due to this reason and the fact that what is done here is intuitive, no further comments will be provided</p>
 */
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    public Permission findById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));
    }

    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
//        return permissionRepository.findByName(name)
//                .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}

