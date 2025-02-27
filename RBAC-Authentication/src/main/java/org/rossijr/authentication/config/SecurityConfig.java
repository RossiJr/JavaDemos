package org.rossijr.authentication.config;

import org.rossijr.authentication.auth.CustomAuthenticationEntryPoint;
import org.rossijr.authentication.auth.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // Inject the allowed origins from the application.properties
    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Method to configure the security pattern (filter chain) for the application.
     * This configuration includes the following <b>(IN THIS APPLICATION)</b>:
     * <ul>
     *     <li>CSRF protection disabled - As stateless authentication is being used</li>
     *     <li>Authorization for the endpoints - This includes:
     *     <ul>
     *         <li>Not requiring authentication for the authentication endpoint and the validation one</li>
     *         <li>Defining the endpoints which will need authentication</li>
     *     </ul>
     *     <li>Session management - As the application is stateless, the session creation policy is set to STATELESS</li>
     *     <li>Adding the JWT Filter</li>
     *     <li>Exception handling - Custom authentication entry point</li>
     * </ul>
     *
     * @param http - The configuration object
     * @return The security filter chain already configured
     * @throws Exception - If there is an error while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        // Matches the authentication endpoint and the validation endpoint
                        .requestMatchers("/api/v1/authentication/login", "/api/v1/authentication/validate").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()          // Require authentication for all other requests
                )
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Configures the Cross-Origin Resource Sharing (CORS) policy for the application.
     *
     * <p>The CORS configuration determines which origins, methods, headers, and other
     * parameters are allowed when clients interact with the API.</p>
     *
     * <h3>In This Application:</h3>
     * <ul>
     *     <li><b>Allowed Origins:</b> Configured dynamically based on the application environment.</li>
     *     <li><b>Allowed Methods:</b> GET, POST, PUT, DELETE, OPTIONS.</li>
     *     <li><b>Allowed Headers:</b> Authorization, Content-Type, Access-Control-Allow-Origin.</li>
     *     <li><b>Allow Credentials:</b> Supports credentials like cookies or tokens.</li>
     *     <li><b>Max Age:</b> Preflight request caching duration (3600 seconds).</li>
     * </ul>
     *
     * <p>This method uses {@link WebMvcConfigurer} to programmatically define the CORS policy.</p>
     *
     * @return A configured {@link WebMvcConfigurer} instance defining the application's CORS policy.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins(allowedOrigins.split(",")) // Support multiple origins from config
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Origin")
                        .allowCredentials(true) // Enable cookies or tokens
                        .maxAge(3600); // Cache preflight responses for 3600 seconds (1 hour)
            }
        };
    }
}
