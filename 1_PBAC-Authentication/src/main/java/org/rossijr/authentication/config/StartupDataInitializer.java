package org.rossijr.authentication.config;

import org.rossijr.authentication.model.Permission;
import org.rossijr.authentication.model.Role;
import org.rossijr.authentication.model.User;
import org.rossijr.authentication.model.UserRole;
import org.rossijr.authentication.repository.RoleRepository;
import org.rossijr.authentication.repository.UserRepository;
import org.rossijr.authentication.service.PermissionService;
import org.rossijr.authentication.service.RolePermissionService;
import org.rossijr.authentication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;

/**
 * Initializes the application with some default data
 * <h5><b>Exclusive for testing purposes!</b></h5>
 */
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
    public CommandLineRunner initializeData(PermissionService permissionService, RoleService roleService, RolePermissionService rolePermissionService) {
        return args -> {
            // Create permissions
            Permission healthCheck = Optional.ofNullable(permissionService.findByName("HEALTH_CHECK"))
                    .orElseGet(() -> permissionService.save(new Permission("HEALTH_CHECK", "Permission to check server")));

            Permission viewUser = Optional.ofNullable(permissionService.findByName("VIEW_USER"))
                    .orElseGet(() -> permissionService.save(new Permission("VIEW_USER", "Permission to view users")));


            User user = new User();
            User admin = new User();
            // Create admin user if not exists
            if (!userRepository.existsByEmail("admin@example.com")) {
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setCreatedAt(ZonedDateTime.now());
                admin.setUpdatedAt(ZonedDateTime.now());
                admin.setRoles(new HashSet<>());
                userRepository.save(admin);
            }


            // Create regular user if not exists
            if (!userRepository.existsByEmail("user@example.com")) {
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setCreatedAt(ZonedDateTime.now());
                user.setUpdatedAt(ZonedDateTime.now());
                user.setRoles(new HashSet<>());

                userRepository.save(user);
            }

            // Create roles and assign permissions
            Role adminRole = Optional.ofNullable(roleService.getRoleByName("ROLE_ADMIN")).orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                role.setDescription("Administrator role with full permissions");
                role.setCreatedBy(admin);
                return roleService.save(role);
            });

            Role userRole = Optional.ofNullable(roleService.getRoleByName("ROLE_USER")).orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_USER");
                role.setDescription("Regular user role with limited permissions");
                role.setCreatedBy(admin);
                return roleService.save(role);
            });

            // Assign permissions to roles
            roleService.assignPermissionToRole(adminRole.getId(), healthCheck.getId(), admin.getId());
            roleService.assignPermissionToRole(adminRole.getId(), viewUser.getId(), admin.getId());


            roleService.assignPermissionToRole(userRole.getId(), viewUser.getId(), admin.getId());

            UserRole userRoleAdmin = new UserRole(admin, roleRepository.findByName("ROLE_ADMIN"));
            UserRole userRoleUser = new UserRole(user, roleRepository.findByName("ROLE_USER"));

            user.getRoles().add(userRoleUser);
            admin.getRoles().add(userRoleAdmin);
            userRepository.save(admin);
            userRepository.save(user);

        };
    }
}
