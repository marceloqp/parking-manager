package br.com.mpsolucoes.manager.domain.repository;

import br.com.mpsolucoes.manager.domain.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Query("SELECT COUNT(*) FROM Spot s WHERE s.sector.id = :sectorId AND s.occupied = true")
    long countSpotsOccupiedBySector(Long sectorId);

}
