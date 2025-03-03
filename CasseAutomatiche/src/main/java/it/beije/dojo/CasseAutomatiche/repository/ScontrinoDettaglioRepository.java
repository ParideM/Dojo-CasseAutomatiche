package it.beije.dojo.CasseAutomatiche.repository;

import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDettaglioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScontrinoDettaglioRepository extends JpaRepository<ScontrinoDettaglioDTO, Long> {
	
    @Query("SELECT SUM(sd.prezzoUnitario * sd.quantita) FROM ScontrinoDettaglioDTO sd WHERE sd.scontrino.id = :scontrinoId")
    BigDecimal calcolaTotaleScontrino(Long scontrinoId);
    
    @Query(value = "SELECT b.codice as barcode, SUM(sd.quantita) as stock " +
            "FROM scontrino_dettaglio sd " +
            "INNER JOIN scontrino s ON s.id = sd.scontrino_id " +
            "INNER JOIN barcode b ON b.id = sd.barcode_id " +
            "WHERE s.data_ora > CURRENT_DATE() " +
            "GROUP BY b.codice", nativeQuery = true)
    List<Object[]> getTotaleQuantitaPerProdotto();
    
    @Query(value = "SELECT b.codice as codice, " +
            "SUM(d.quantita) AS pezziVenduti, " +
            "SUM(d.quantita * d.prezzo_unitario) AS incasso " +
            "FROM scontrino_dettaglio d " +
            "JOIN scontrino s ON d.scontrino_id = s.id " +
            "JOIN barcode b ON b.id = d.barcode_id " +
            "WHERE DATE(s.data_ora) = :data " +
            "GROUP BY b.codice", 
    nativeQuery = true)
    List<Object[]> getIncassoEPezziPerProdotto(@Param("data") LocalDate data);
    
    @Query(value = "SELECT r.nome, SUM(d.quantita * d.prezzo_unitario) AS incasso "
    		+ "FROM scontrino_dettaglio d "
    		+ "JOIN scontrino s ON d.scontrino_id = s.id "
    		+ "join barcode b on b.id = d.barcode_id "
    		+ "join prodotto p on p.id = b.prodotto_id "
    		+ "join reparto r on r.id = p.reparto_id "
    		+ "WHERE DATE(s.data_ora) =  :data "
    		+ "GROUP BY r.nome", 
    nativeQuery = true)
    List<Object[]> getIncassoPerReparto(@Param("data") LocalDate data);
    
    @Query(value = "SELECT r.nome, SUM(d.quantita * d.prezzo_unitario) AS incasso "
    		+ "FROM scontrino_dettaglio d "
    		+ "JOIN scontrino s ON d.scontrino_id = s.id "
    		+ "join barcode b on b.id = d.barcode_id "
    		+ "join prodotto p on p.id = b.prodotto_id "
    		+ "join reparto r on r.id = p.reparto_id "
    		+ "WHERE YEAR(s.data_ora) =  :anno "
    		+ "GROUP BY r.nome", 
    nativeQuery = true)
    List<Object[]> getIncassoPerRepartoDatoAnno(@Param("anno") String anno);
    
    
}