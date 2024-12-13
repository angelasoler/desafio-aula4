package acc.br.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for setting up security rules.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean for password encoding using BCrypt.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for in-memory user details service with predefined users and roles.
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build());
        return manager;
    }

    /**
     * Bean for configuring HTTP security with authorization and authentication rules.
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/public/**").permitAll()  // Allow unauthenticated access to login and public pages
                        .requestMatchers("/admin/**").hasRole("ADMIN")       // Restrict access to /admin/** to ADMIN role only
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // Allow access to /user/** for USER and ADMIN roles
                        .anyRequest().authenticated()                        // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")                                // Custom login page
                        .defaultSuccessUrl("/home", true)                   // Redirect to home after successful login
                        .permitAll()                                        // Allow all to access login page
                )
                .logout(logout -> logout
                        .permitAll()                                        // Allow all to log out
                );

        return http.build();
    }
}