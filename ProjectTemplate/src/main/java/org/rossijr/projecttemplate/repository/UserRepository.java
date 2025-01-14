package org.rossijr.projecttemplate.repository;

import org.rossijr.projecttemplate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
