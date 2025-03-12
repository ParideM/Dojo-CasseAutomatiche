package it.beije.dojo.CasseAutomatiche.service;

import it.beije.dojo.CasseAutomatiche.dto.UtenteDTO;
import it.beije.dojo.CasseAutomatiche.repository.UtenteRepository;
import it.beije.dojo.CasseAutomatiche.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UtenteRepository utenteRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UtenteRepository utenteRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String username, String password) {
    	
        UtenteDTO utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(password, utente.getPasswordHash())) {
            // Generate and return JWT token
            return jwtUtil.generateToken(username, utente.getRuolo().name());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
