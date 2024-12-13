package acc.br.login.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import acc.br.login.model.entity.User;
import acc.br.login.service.UserValidationService;

import acc.br.login.model.entity.LoginRequest;

@RestController
public class RegistrationController {
    @Autowired
    private UserValidationService userValidationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest registrationRequest) {
        try {
            User newUser = userValidationService.registerNewUser(
                registrationRequest.getUsername(), 
                registrationRequest.getPassword()
            );
            return ResponseEntity.ok(Map.of(
                "message", "Registration successful",
                "userId", newUser.getId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
}
