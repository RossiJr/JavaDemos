package org.rossijr.authentication.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rossijr.authentication.config.dto.ExceptionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource that requires authentication.
     *
     * <p>Important, this is not part of GlobalExceptionHandler, this is a specific handler for authentication exceptions.</p>
     *
     * @param request       *Not important to describe for this context*
     * @param response      *Not important to describe for this context*
     * @param authException *Not important to describe for this context*
     * @throws IOException *Not important to describe for this context*
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Invalid username or password",
                request.getRequestURI()
        );

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponseDTO));
    }
}
