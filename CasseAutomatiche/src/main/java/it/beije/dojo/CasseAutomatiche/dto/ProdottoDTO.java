package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prodotto")
public class ProdottoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false)
    private Integer grammatura;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JsonIgnore
    @JoinColumn(name = "reparto_id", referencedColumnName = "id")
    private RepartoDTO reparto;

    @Column(nullable = false)
    private String unitaMisura;

    private LocalDateTime dataDisattivazione;
}