package br.com.mpsolucoes.manager.domain.repository;

import br.com.mpsolucoes.manager.domain.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Parking p WHERE p.spot.sector.sectorName = :sector AND p.exitTime BETWEEN :start AND :end")
    BigDecimal findTotalRevenueBySectorAndDate(String sector, LocalDateTime start, LocalDateTime end);

    @Query("SELECT p FROM Parking p WHERE p.spot.id = :spotId AND p.exitTime is null")
    Optional<Parking> findActualParkingBySpotId(Long spotId);

    @Query("SELECT p FROM Parking p WHERE p.spot.licensePlate = :plate AND p.exitTime is null")
    Optional<Parking> findActualParkingByPlate(String plate);
}
