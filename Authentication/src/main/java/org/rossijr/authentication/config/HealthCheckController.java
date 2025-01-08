package org.rossijr.authentication.config;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthCheckController {

    /**
     * Public health check endpoint accessible to users with the USER role.
     *
     * @return a response indicating the public server health status.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<String> userHealthCheck() {
        return ResponseEntity.ok("Public Health Check: Server is running and accessible to USER role.");
    }

    /**
     * Admin health check endpoint accessible only to users with the ADMIN role.
     *
     * @return a response indicating the admin server health status.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminHealthCheck() {
        return ResponseEntity.ok("Admin Health Check: Server is running and accessible to ADMIN role.");
    }
}
