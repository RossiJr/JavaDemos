package org.rossijr.authentication.auth;

import org.rossijr.authentication.model.User;
import org.rossijr.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom implementation of {@link UserDetailsService} to load user-specific data for authentication.
 *
 * <p>This service retrieves user details from the application's {@link UserRepository} by email
 * (used as the username). It integrates with Spring Security by providing a {@link UserDetails}
 * object containing the user's credentials and authorities for authentication and authorization.</p>
 *
 * <h3>Key Responsibilities:</h3>
 * <ul>
 *   <li>Validates the input username (email).</li>
 *   <li>Fetches the user details from the database using the {@link UserRepository}.</li>
 *   <li>Converts the {@link User} entity into a {@link UserDetails} object.</li>
 *   <li>Throws a {@link UsernameNotFoundException} if the user is not found.</li>
 * </ul>
 *
 * @see UserDetailsService
 * @see UserRepository
 **/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for injecting the {@link UserRepository}.
     *
     * @param userRepository the repository used to fetch user data
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username (email) for authentication purposes.
     *
     * <p>This method retrieves user details from the {@link UserRepository} based on the provided
     * username, which is an email field. If the username is null or blank, it throws an {@link IllegalArgumentException}.
     * If the user is not found in the database, it throws a {@link UsernameNotFoundException}.
     * Otherwise, it converts the user entity into a {@link UserDetails} object containing the user's credentials
     * and authorities for authentication and authorization.</p>
     *
     * @param username the email of the user to load
     * @return a {@link UserDetails} object containing the user's credentials and authorities
     * @throws IllegalArgumentException  if the username is null or blank
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty() || username.length() > 255) {
            throw new IllegalArgumentException("Invalid username format");
        }
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
