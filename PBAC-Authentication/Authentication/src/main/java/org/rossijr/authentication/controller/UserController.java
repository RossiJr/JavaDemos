package org.rossijr.authentication.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.rossijr.authentication.dto.CreateUserRequestDTO;
import org.rossijr.authentication.dto.CreateUserResponseDTO;
import org.rossijr.authentication.dto.GetUserResponseDTO;
import org.rossijr.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user in the system.
     *
     * <p>This method is secured using the {@code @PreAuthorize} annotation to restrict access to users
     * with the {@code CREATE_USER} permission. Only authenticated users with this permission can perform
     * this operation.</p>
     *
     * @param userRequestDTO a {@link CreateUserRequestDTO} object containing the details of the user
     *                       to be created. This object must pass validation checks.
     * @return a {@link ResponseEntity} containing the details of the newly created user wrapped in a
     * {@link CreateUserResponseDTO} object, along with an HTTP status of {@code 201 CREATED}.
     */
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO userRequestDTO) {
        CreateUserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    /**
     * Retrieves user data based on the provided user ID.
     *
     * <p>This method is secured using the {@code @PreAuthorize} annotation to ensure that only the
     * authenticated user can access their own data. The method checks whether the provided user ID
     * matches the ID of the authenticated user - it is possible to create your own annotation to
     * check this constraint (the user accessing only its own data) you can read more about it in the
     * Spring Security documentation.</p>
     *
     * <p>The method uses a UUID-based ID for user identification. If the provided ID does not match
     * the authenticated user's ID, the request will result in a {@code 403 Forbidden} response.</p>
     *
     * @param id    the UUID of the user whose data is being requested, provided as a path variable.
     *              This value must be non-blank and valid.
     * @param token the authentication token of the user making the request, provided as a header (it extracts
     *              automatically from the request header).
     * @return a {@link ResponseEntity} containing the user's data wrapped in a
     * {@link GetUserResponseDTO} object.
     * @throws IllegalArgumentException if the provided {@code id} is not a valid UUID.
     */
    @PreAuthorize("T(java.util.UUID).fromString(#id).equals(@securityUtils.getAuthenticatedUserId(#token))")
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDTO> getUser(@RequestHeader("Authorization") String token, @Valid @NotBlank @PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(UUID.fromString(id)));
    }
}
