package it.beije.dojo.CasseAutomatiche.service;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import it.beije.dojo.CasseAutomatiche.model.Prodotto;
import it.beije.dojo.CasseAutomatiche.repository.ScontrinoDettaglioRepository;
import it.beije.dojo.CasseAutomatiche.repository.ScontrinoRepository;

@Service
public class IncassoService {

    private final ScontrinoRepository scontrinoRepository;
    private final ScontrinoDettaglioRepository scontrinoDettaglioRepository;

    public IncassoService(ScontrinoRepository scontrinoRepository, ScontrinoDettaglioRepository scontrinoDettaglioRepository) {
        this.scontrinoRepository = scontrinoRepository;
        this.scontrinoDettaglioRepository = scontrinoDettaglioRepository;
    }

    public Double getIncassoGiornaliero() {
        return scontrinoRepository.getIncassoGiornaliero();
    }
    
    public List<Prodotto> getVenditeEIncassoPerData(LocalDate data) {
        List<Object[]> query = scontrinoDettaglioRepository.getIncassoEPezziPerProdotto(data);

        return query.stream().map(q -> new Prodotto((String)q[0],(BigDecimal) q[1],((Number) q[2]).longValue())
        		).toList();
    }
    
    public List<Prodotto> getIncassoPerReparto(LocalDate data) {
        List<Object[]> query = scontrinoDettaglioRepository.getIncassoPerReparto(data);

        return query.stream().map(q -> new Prodotto((String)q[0],(BigDecimal) q[1])
        		).toList();
    }

	public List<Prodotto> getIncassoPerRepartoDatoAnno(String anno) {
        List<Object[]> query = scontrinoDettaglioRepository.getIncassoPerRepartoDatoAnno(anno);

        return query.stream().map(q -> new Prodotto((String)q[0],(BigDecimal) q[1])
        		).toList();
	}
}