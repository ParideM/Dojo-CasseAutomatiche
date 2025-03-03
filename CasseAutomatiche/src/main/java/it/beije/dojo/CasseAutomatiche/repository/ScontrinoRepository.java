package it.beije.dojo.CasseAutomatiche.repository;

import it.beije.dojo.CasseAutomatiche.dto.ScontrinoDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScontrinoRepository extends JpaRepository<ScontrinoDTO, Long> {

    @Query(value = "SELECT COALESCE(SUM(s.totale), 0) FROM scontrino s WHERE DATE(s.data_ora) = CURRENT_DATE", nativeQuery = true)
    Double getIncassoGiornaliero();

}