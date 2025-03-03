package it.beije.dojo.CasseAutomatiche.service;

import it.beije.dojo.CasseAutomatiche.dto.BarcodeDTO;
import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDTO;
import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDettaglioDTO;
import it.beije.dojo.CasseAutomatiche.dto.StoricoPrezziStockDTO;
import it.beije.dojo.CasseAutomatiche.repository.BarcodeRepository;
import it.beije.dojo.CasseAutomatiche.repository.ScontrinoDettaglioRepository;
import it.beije.dojo.CasseAutomatiche.repository.ScontrinoRepository;
import it.beije.dojo.CasseAutomatiche.repository.StoricoPrezziStockRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScontrinoService {

    private final ScontrinoRepository scontrinoRepository;
    private final ScontrinoDettaglioRepository scontrinoDettaglioRepository;
    private final BarcodeRepository barcodeRepository;
    private final StoricoPrezziStockRepository storicoPrezzoStockRepository;

    public ScontrinoService(ScontrinoRepository scontrinoRepository,
                            ScontrinoDettaglioRepository scontrinoDettaglioRepository,
                            BarcodeRepository barcodeRepository, StoricoPrezziStockRepository storicoPrezzoStockRepository) {
    	
        this.scontrinoRepository = scontrinoRepository;
        this.scontrinoDettaglioRepository = scontrinoDettaglioRepository;
        this.barcodeRepository = barcodeRepository;
        this.storicoPrezzoStockRepository = storicoPrezzoStockRepository;

    }

    @Transactional
    public ScontrinoDTO createScontrino(ScontrinoDTO scontrinoDto) {
    	
    	scontrinoDto = scontrinoDto == null ? new ScontrinoDTO() : scontrinoDto;
        return scontrinoRepository.save(scontrinoDto);
    }
    
    @Transactional
    public ScontrinoDTO updateScontrino(Long id, ScontrinoDTO scontrinoDto) {
        ScontrinoDTO existing = scontrinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scontrino non trovato"));

        BigDecimal totale = scontrinoDettaglioRepository.calcolaTotaleScontrino(id);
        existing.setTotale(totale);
        existing.setMetodoPagamento(scontrinoDto.getMetodoPagamento());
        existing.setImportoPagato(scontrinoDto.getImportoPagato());
        existing.setResto(scontrinoDto.getImportoPagato().subtract(totale));
        existing.setDataOra(LocalDateTime.now());
        
        return scontrinoRepository.save(existing);
    }
    
    @Transactional
    public ScontrinoDettaglioDTO addArticolo(Long scontrinoId, BarcodeDTO barcodeDTO) {
    	
        ScontrinoDTO scontrino = scontrinoRepository.findById(scontrinoId)
                .orElseThrow(() -> new RuntimeException("Scontrino non trovato"));


        String codice = barcodeDTO.getCodice();
        BarcodeDTO barcode = barcodeRepository.findByCodice(codice)
                .orElseThrow(() -> new RuntimeException("Barcode non trovato"));
        
        if (barcode.getDataFineValidita() != null && barcode.getDataFineValidita().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Barcode scaduto");
        }

        StoricoPrezziStockDTO storico = storicoPrezzoStockRepository.findByBarcodeAndData(barcode, LocalDate.now())
        		.orElseThrow(() -> new RuntimeException("Nessun record di prezzo trovato per il barcode " + codice));
        
        ScontrinoDettaglioDTO nuovoDettaglio = new ScontrinoDettaglioDTO();
        nuovoDettaglio.setScontrino(scontrino);
        nuovoDettaglio.setBarcode(barcode);
        //assumo che ogni barcode anche se uguale sia una nuova riga a db
        nuovoDettaglio.setQuantita(1);
        nuovoDettaglio.setPrezzoUnitario(storico.getPrezzo());

        return scontrinoDettaglioRepository.save(nuovoDettaglio);
    }
}
