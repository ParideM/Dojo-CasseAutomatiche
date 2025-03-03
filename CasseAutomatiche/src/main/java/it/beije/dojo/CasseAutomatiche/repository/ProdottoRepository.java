package it.beije.dojo.CasseAutomatiche.repository;

import it.beije.dojo.CasseAutomatiche.dto.ProdottoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository extends JpaRepository<ProdottoDTO, Long> {
    // Puoi aggiungere metodi di query personalizzati se necessario
}