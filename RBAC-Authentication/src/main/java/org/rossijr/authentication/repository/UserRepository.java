package org.rossijr.authentication.repository;

import org.rossijr.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user SET last_login = now() WHERE email = :email", nativeQuery = true)
    void updateLastLogin(String email);
}