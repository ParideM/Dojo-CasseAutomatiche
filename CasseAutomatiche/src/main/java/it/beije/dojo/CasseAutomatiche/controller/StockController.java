package it.beije.dojo.CasseAutomatiche.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.beije.dojo.CasseAutomatiche.model.Prodotto;
import it.beije.dojo.CasseAutomatiche.service.StockService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stock")
@Slf4j
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/giornaliero")
    public ResponseEntity<?> calcolaStockGiornata() {
        try {
            List<Prodotto> stockFinale = stockService.calcolaStockGiornata();
            return ResponseEntity.ok(stockFinale);
        } catch (Exception e) {
        	log.error("Errore durante il calcolo dello stock giornaliero", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore nel calcolo dello stock giornaliero", "message", e.getMessage()));
        }
    }
}
