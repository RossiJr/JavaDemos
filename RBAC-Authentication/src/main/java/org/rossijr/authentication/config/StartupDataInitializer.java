package org.rossijr.authentication.config;

import org.rossijr.authentication.model.Role;
import org.rossijr.authentication.model.User;
import org.rossijr.authentication.model.UserRole;
import org.rossijr.authentication.repository.RoleRepository;
import org.rossijr.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.HashSet;

@Configuration
public class StartupDataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StartupDataInitializer(RoleRepository roleRepository,
                                  UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create roles if they don't exist
            if (!roleRepository.existsByName("ROLE_USER")) {
                roleRepository.save(new Role("ROLE_USER"));
            }

            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }

            // Create admin user if not exists
            if (!userRepository.existsByEmail("admin@example.com")) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setCreatedAt(ZonedDateTime.now());
                admin.setUpdatedAt(ZonedDateTime.now());
                admin.setRoles(new HashSet<>());

                UserRole userRole = new UserRole(admin, roleRepository.findByName("ROLE_ADMIN"));

                admin.getRoles().add(userRole);

                userRepository.save(admin);
            }

            // Create regular user if not exists
            if (!userRepository.existsByEmail("user@example.com")) {
                User user = new User();
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(new HashSet<>());

                UserRole userRole = new UserRole(user, roleRepository.findByName("ROLE_USER"));

                user.getRoles().add(userRole);
                userRepository.save(user);
            }
        };
    }
}