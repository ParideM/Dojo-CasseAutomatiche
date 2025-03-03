package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scontrino_dettaglio")
public class ScontrinoDettaglioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "scontrino_id", referencedColumnName = "id", nullable = false)
    private ScontrinoDTO scontrino;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JsonIgnore
    @JoinColumn(name = "barcode_id", referencedColumnName = "id", nullable = false)
    private BarcodeDTO barcode;

    @Column(nullable = false)
    private Integer quantita;

    @Column(nullable = false)
    private BigDecimal prezzoUnitario;
}
