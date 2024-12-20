package acc.br.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;



/**
 * Security configuration class for setting up security rules.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
			.csrf(csrf -> csrf.disable())  // Importante para testes
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/register", "/login", "/h2-console/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("http://localhost:8081/student/form", true)
                .failureUrl("/login?error=true")
			)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
			.userDetailsService(userDetailsService)
			.headers(headers -> headers.frameOptions(frame -> frame.disable())); // Para H2 Console

        return http.build();
    }
}