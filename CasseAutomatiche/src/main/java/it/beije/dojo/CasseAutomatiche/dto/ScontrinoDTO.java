package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scontrino")
public class ScontrinoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataOra;

    private BigDecimal totale;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    private BigDecimal importoPagato;

    private BigDecimal resto;

    public enum MetodoPagamento {
        CONTANTI, CARTA
    }
}
