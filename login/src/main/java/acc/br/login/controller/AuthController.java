package acc.br.login.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import acc.br.login.model.entity.LoginRequest;

@RestController
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (!isValidCredentials(loginRequest)) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
        }

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    private boolean isValidCredentials(LoginRequest request) {
        return false; // temporário
    }
}
