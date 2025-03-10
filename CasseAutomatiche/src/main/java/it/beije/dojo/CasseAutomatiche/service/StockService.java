package it.beije.dojo.CasseAutomatiche.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.beije.dojo.CasseAutomatiche.dto.StoricoPrezziStockDTO;
import it.beije.dojo.CasseAutomatiche.model.Prodotto;

import it.beije.dojo.CasseAutomatiche.repository.ScontrinoDettaglioRepository;

import it.beije.dojo.CasseAutomatiche.repository.StoricoPrezziStockRepository;

@Service
public class StockService {

    private final ScontrinoDettaglioRepository scontrinoDettaglioRepository;
    private final StoricoPrezziStockRepository storicoPrezzoStockRepository;

    public StockService(ScontrinoDettaglioRepository scontrinoDettaglioRepository,
    		StoricoPrezziStockRepository storicoPrezzoStockRepository) {
    	
        this.scontrinoDettaglioRepository = scontrinoDettaglioRepository;
        this.storicoPrezzoStockRepository = storicoPrezzoStockRepository;

    }

    public List<Prodotto> calcolaStockGiornata() {

    	List<Object[]> results = scontrinoDettaglioRepository.getTotaleQuantitaPerProdotto();
    	
    	//metto i dati sugli articoli venduti in una mappa, per ottimizzare poi accopiamento con la lista di tutti gli articoli
    	Map<String, Prodotto> venduti = results.stream()
    	    .map(result -> new Prodotto((String) result[0], ((Number) result[1]).longValue()))
    	    .collect(Collectors.toMap(Prodotto::getBarcode, venduto -> venduto));
    	
    	List<StoricoPrezziStockDTO> oldStock = storicoPrezzoStockRepository.findAllByData(LocalDate.now());
    	List<Prodotto> newStock = oldStock.stream().map(old -> {
    		
    		Prodotto venduto = venduti.get(old.getBarcode().getCodice());
    		
    		//aggiorno quantita residua
    		return new Prodotto(old.getBarcode().getCodice(), old.getQuantita() - (venduto == null? 0 : venduto.getStock()));
   		
    	}).toList();
    	
 

        return newStock;
    }
}
