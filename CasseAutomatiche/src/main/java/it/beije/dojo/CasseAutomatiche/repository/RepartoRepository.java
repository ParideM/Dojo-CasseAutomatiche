package it.beije.dojo.CasseAutomatiche.repository;

import it.beije.dojo.CasseAutomatiche.dto.RepartoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartoRepository extends JpaRepository<RepartoDTO, Long> {
    
}
