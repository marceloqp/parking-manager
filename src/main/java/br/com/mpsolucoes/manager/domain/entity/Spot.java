package br.com.mpsolucoes.manager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "spot", schema = "parking_manager")
@Data
@NoArgsConstructor
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sector;

    @Column
    private Double lat;

    @Column
    private Double lng;

    @Column
    private boolean occupied;

    @Column
    private String licensePlate;

    @Column
    private LocalDateTime entryTime;

}