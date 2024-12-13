package acc.br.login.controller;

import acc.br.login.model.entity.LoginRequest;
import acc.br.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            
            String token = jwtUtil.generateToken(authentication.getName());
            
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // dev env false, trun it true on prod
            cookie.setPath("/");
            cookie.setMaxAge((int) (jwtUtil.getJwtExpirationMs() / 1000));
            response.addCookie(cookie);

            try {
                response.sendRedirect("http://localhost:8081/student/form");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return ResponseEntity.ok(Map.of(
                "message", "Login successful"
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity
                .status(401)
                .body(Map.of("message", "Invalid username or password"));
        }
    }
}
