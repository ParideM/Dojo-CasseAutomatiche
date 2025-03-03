package it.beije.dojo.CasseAutomatiche.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
public class Prodotto {
    
    private String barcode;
    private BigDecimal incasso;
    private Long stock;
    private String reparto;

    public Prodotto(String barcode, Long stock) {
        this.barcode = barcode;
        this.stock = stock;
    }
    public Prodotto(String reparto, BigDecimal incasso) {
        this.reparto = reparto;
        this.incasso = incasso;
    }
    public Prodotto(String barcode,  BigDecimal incasso, Long stock) {
        this.barcode = barcode;
        this.incasso = incasso;
        this.stock = stock;
        
    }
}
