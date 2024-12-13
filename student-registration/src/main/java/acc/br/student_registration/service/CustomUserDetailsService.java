package acc.br.studentregistration.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    // qualquer usuário com JWT válido tem ROLE_USER
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username)
                   .password("") // Sem senha necessária
                   .roles("USER")
                   .build();
    }
}
