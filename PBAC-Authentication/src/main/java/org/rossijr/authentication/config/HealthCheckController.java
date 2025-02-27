package org.rossijr.authentication.config;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * THIS IS ONLY A MOCK CONTROLLER FOR DEMONSTRATION PURPOSES.
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthCheckController {

    /**
     * Public health check endpoint accessible to users with the VIEW_USER permission (authority).
     *
     * @return a mock response indicating the server health status.
     */
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userHealthCheck() {
        return ResponseEntity.ok("Public Health Check: Server is running and accessible to USER role.");
    }

    /**
     * Admin health check endpoint accessible to users with the HEALTH_CHECK permission (authority).
     *
     * @return a mock response indicating the server health status.
     */
    @PreAuthorize("hasAuthority('HEALTH_CHECK')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminHealthCheck() {
        return ResponseEntity.ok("Admin Health Check: Server is running and accessible to ADMIN role.");
    }

}
