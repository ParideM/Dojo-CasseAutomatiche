package it.beije.dojo.CasseAutomatiche.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.beije.dojo.CasseAutomatiche.model.Prodotto;
import it.beije.dojo.CasseAutomatiche.service.IncassoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/incassi")
@Slf4j
public class IncassoController {

    private final IncassoService incassoService;

    public IncassoController(IncassoService incassoService) {
        this.incassoService = incassoService;
    }

    @GetMapping("/giornaliero")
    public ResponseEntity<?> getIncassoGiornaliero() {
        try {
            Double incasso = incassoService.getIncassoGiornaliero();
            return ResponseEntity.ok(incasso);
        } catch (Exception e) {
        	log.error("Errore durante il calcolo dell'incasso giornaliero", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel recupero dell'incasso giornaliero", "message", e.getMessage()));
        }
    }
    
    @GetMapping("/pezzi/giorno")
    public ResponseEntity<?> getVenditeEIncassoPerData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            if (data == null) {
                return ResponseEntity.badRequest().body("La data non deve essere nulla.");
            }
            List<Prodotto> vendite = incassoService.getVenditeEIncassoPerData(data);
            return ResponseEntity.ok(vendite);
        } catch (Exception e) {
        	 log.error("Errore durante il calcolo delle vendite e incasso per la data: {}", data, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel recupero delle vendite e incassi per la data", "message", e.getMessage()));
        }
    }
    
    @GetMapping("/reparto")
    public ResponseEntity<?> getIncassoPerReparto(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            if (data == null) {
                return ResponseEntity.badRequest().body("La data non deve essere nulla.");
            }
            List<Prodotto> vendite = incassoService.getIncassoPerReparto(data);
            return ResponseEntity.ok(vendite);
        } catch (Exception e) {
            log.error("Errore durante il calcolo dell'incasso per reparto nella data: {}", data, e);       
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel recupero dell'incasso per reparto", "message", e.getMessage()));
        }
    }
    
    @GetMapping("/reparto/annuale")
    public ResponseEntity<?> getIncassoPerRepartoDatoAnno(
            @RequestParam String anno) {
        try {
            if (anno == null) {
                return ResponseEntity.badRequest().body("anno non deve essere nullo.");
            }
            List<Prodotto> vendite = incassoService.getIncassoPerRepartoDatoAnno(anno);
            return ResponseEntity.ok(vendite);
        } catch (Exception e) {
            log.error("Errore durante il calcolo dell'incasso per reparto nell'anno: {}", anno, e);           
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel recupero dell'incasso annuale per reparto", "message", e.getMessage()));
        }
    }
}
