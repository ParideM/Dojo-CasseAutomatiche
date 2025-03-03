package it.beije.dojo.CasseAutomatiche.repository;

import it.beije.dojo.CasseAutomatiche.dto.BarcodeDTO;
import it.beije.dojo.CasseAutomatiche.dto.StoricoPrezziStockDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoricoPrezziStockRepository extends JpaRepository<StoricoPrezziStockDTO, Long> {

	Optional<StoricoPrezziStockDTO> findByBarcodeAndData(BarcodeDTO barcode, LocalDate data);
	
	List<StoricoPrezziStockDTO> findAllByData(LocalDate data);
	
}