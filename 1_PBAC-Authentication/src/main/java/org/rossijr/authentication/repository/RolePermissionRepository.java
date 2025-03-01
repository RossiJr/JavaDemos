package org.rossijr.authentication.repository;

import org.rossijr.authentication.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RolePermission entity
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
