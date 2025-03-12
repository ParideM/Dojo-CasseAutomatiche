package it.beije.dojo.CasseAutomatiche.controller;

import it.beije.dojo.CasseAutomatiche.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            // Call the authentication service to generate the JWT
            String jwt = authenticationService.authenticate(username, password);
            return ResponseEntity.ok(Map.of("token", jwt));  // Return the token in the response
        } catch (Exception e) {
            log.error("Errore nel login con username: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel login", "message", e.getMessage()));
        }
    }
}
