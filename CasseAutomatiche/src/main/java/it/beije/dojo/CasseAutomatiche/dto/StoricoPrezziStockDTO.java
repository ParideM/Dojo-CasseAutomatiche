package it.beije.dojo.CasseAutomatiche.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "storico_prezzi_stock")
public class StoricoPrezziStockDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "barcode_id", referencedColumnName = "id", nullable = false)
    private BarcodeDTO barcode;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal prezzo;

    @Column(nullable = false)
    private Integer quantita;
}