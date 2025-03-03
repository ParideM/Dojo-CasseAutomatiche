package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "barcode")
public class BarcodeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JsonIgnore
    @JoinColumn(name = "prodotto_id", referencedColumnName = "id", nullable = false)
    private ProdottoDTO prodotto;

    @Column(nullable = false, length = 50, unique = true)
    private String codice;

    @Column(nullable = false)
    private LocalDate dataInizioValidita;

    private LocalDate dataFineValidita;
}