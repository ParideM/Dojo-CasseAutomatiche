package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "utente")
public class UtenteDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    @Column(name = "data_disattivazione")
    private LocalDateTime dataDisattivazione;
    
    public enum Ruolo {
        CASSIERE,
        CONSUMATORE,
        ADMIN
    }
    
}