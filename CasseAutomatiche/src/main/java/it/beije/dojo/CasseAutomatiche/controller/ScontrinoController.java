package it.beije.dojo.CasseAutomatiche.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.beije.dojo.CasseAutomatiche.dto.BarcodeDTO;
import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDTO;
import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDettaglioDTO;
import it.beije.dojo.CasseAutomatiche.service.ScontrinoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/scontrini")
@Slf4j
public class ScontrinoController {
    
    private final ScontrinoService scontrinoService;
    
    public ScontrinoController(ScontrinoService scontrinoService) {
        this.scontrinoService = scontrinoService;
    }
    
    @PostMapping
    public ResponseEntity<?> createScontrino(@RequestBody ScontrinoDTO scontrinoDto) {
        try {
            ScontrinoDTO created = scontrinoService.createScontrino(scontrinoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            log.error("Errore nella creazione dello scontrino con i dati: {}", scontrinoDto, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nella creazione dello scontrino", "message", e.getMessage()));
        }
    }
    
    @PostMapping("/{scontrinoId}/articoli")
    public ResponseEntity<?> addArticoloToScontrino(
            @PathVariable Long scontrinoId,
            @RequestBody BarcodeDTO barcodeDTO) {
        try {
            if (barcodeDTO == null) {
                return ResponseEntity.badRequest().body("barcode assente.");
            }
            ScontrinoDettaglioDTO savedDettaglio = scontrinoService.addArticolo(scontrinoId, barcodeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDettaglio);
        } catch (Exception e) {
            log.error("Errore nell'aggiunta dell'articolo al scontrino ID: {} con i dati articolo: {}", scontrinoId, barcodeDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nell'aggiunta dell'articolo", "message", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateScontrino(@PathVariable Long id, @RequestBody ScontrinoDTO scontrinoDto) {
        try {
            ScontrinoDTO updated = scontrinoService.updateScontrino(id, scontrinoDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Errore nell'aggiornamento dello scontrino ID: {} con i dati: {}", id, scontrinoDto, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nell'aggiornamento dello scontrino", "message", e.getMessage()));
        }
    }
}
