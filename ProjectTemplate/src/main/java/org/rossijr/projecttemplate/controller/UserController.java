package org.rossijr.projecttemplate.controller;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.rossijr.projecttemplate.dto.CreateUserRequestDTO;
import org.rossijr.projecttemplate.dto.CreateUserResponseDTO;
import org.rossijr.projecttemplate.dto.GetUserResponseDTO;
import org.rossijr.projecttemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO userRequestDTO) {
        CreateUserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }



    @PreAuthorize("T(java.util.UUID).fromString(#id) == principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDTO> getUser(@Valid @NotBlank @PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(UUID.fromString(id)));
    }
}
