package br.com.mpsolucoes.manager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "sector", schema = "parking_manager")
@Data
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sector")
    private String sectorName;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "max_parking_time", nullable = false)
    private Integer maxParkingTime;

    @Column(name = "open_hour")
    private LocalTime openHour;

    @Column(name = "close_hour")
    private LocalTime closeHour;

}

