package org.rossijr.projecttemplate.service;

import org.rossijr.projecttemplate.dto.CreateUserRequestDTO;
import org.rossijr.projecttemplate.dto.CreateUserResponseDTO;
import org.rossijr.projecttemplate.dto.GetUserResponseDTO;
import org.rossijr.projecttemplate.exception.EmailAlreadyInUseExceptionWeb;
import org.rossijr.projecttemplate.exception.ObjectNotFoundException;
import org.rossijr.projecttemplate.model.User;
import org.rossijr.projecttemplate.model.UserRole;
import org.rossijr.projecttemplate.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    private boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public CreateUserResponseDTO createUser(CreateUserRequestDTO userRequestDTO) {
        try {
            if (userRequestDTO == null) {
                throw new IllegalArgumentException("The user cannot be null");
            }
            if (!isEmailUnique(userRequestDTO.getEmail())) {
                throw new EmailAlreadyInUseExceptionWeb("This email is already in use");
            }

            // Map DTO to entity
            User user = new User();
            user.setEmail(userRequestDTO.getEmail());
            user.setPassword(hashPassword(userRequestDTO.getPassword()));
            user.setCreatedAt(ZonedDateTime.now());
            user.setUpdatedAt(ZonedDateTime.now());
            user.setRoles(new HashSet<>());

            // Assign roles
            UserRole userRole = new UserRole(user, roleService.getRoleByName("ROLE_USER"));

            user.getRoles().add(userRole);

            // Map entity to DTO
            User savedUser = userRepository.save(user);
            return new CreateUserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getCreatedAt());
        } catch (IllegalArgumentException | EmailAlreadyInUseExceptionWeb e) {
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating the user: {}", e.getMessage());
            throw e;
        }
    }


    public GetUserResponseDTO getUser(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ObjectNotFoundException("User not found");
        }

        return new GetUserResponseDTO(user.getId(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt(),
                user.getRoles().stream().map(UserRole::getRole).collect(Collectors.toSet()));
    }
}
