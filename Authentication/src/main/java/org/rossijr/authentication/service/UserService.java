package org.rossijr.authentication.service;

import org.rossijr.authentication.dto.CreateUserRequestDTO;
import org.rossijr.authentication.dto.CreateUserResponseDTO;
import org.rossijr.authentication.dto.GetUserResponseDTO;
import org.rossijr.authentication.exception.EmailAlreadyInUseExceptionWeb;
import org.rossijr.authentication.exception.ObjectNotFoundException;
import org.rossijr.authentication.model.User;
import org.rossijr.authentication.model.UserRole;
import org.rossijr.authentication.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    private boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param userRequestDTO the data transfer object containing user details
     * @return a data transfer object containing the created user's details
     * @throws IllegalArgumentException   if the provided user details are invalid
     * @throws EmailAlreadyInUseExceptionWeb if the email is already associated with an existing user
     */
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

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a data transfer object containing the user's details
     * @throws IllegalArgumentException if the user with the provided ID does not exist
     */
    public GetUserResponseDTO getUser(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ObjectNotFoundException("User not found");
        }

        return new GetUserResponseDTO(user.getId(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt(),
                user.getRoles().stream().map(UserRole::getRole).collect(Collectors.toSet()));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
