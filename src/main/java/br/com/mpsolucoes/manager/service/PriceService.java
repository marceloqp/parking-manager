package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.entity.Sector;
import br.com.mpsolucoes.manager.domain.entity.Spot;
import br.com.mpsolucoes.manager.domain.repository.SpotRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static br.com.mpsolucoes.manager.domain.enums.OccupancyCategory.BELOW_100;
import static br.com.mpsolucoes.manager.domain.enums.OccupancyCategory.BELOW_25;
import static br.com.mpsolucoes.manager.domain.enums.OccupancyCategory.BELOW_50;
import static br.com.mpsolucoes.manager.domain.enums.OccupancyCategory.BELOW_75;
import static br.com.mpsolucoes.manager.domain.enums.OccupancyCategory.getOccupancyCategory;

@Service
public class PriceService {

    private final SpotRepository spotRepository;

    public PriceService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public BigDecimal calculatePrice(Spot spot, LocalDateTime exitTime) {

        if (spot.getEntryTime() == null || exitTime == null) {
            return BigDecimal.ZERO;
        }

        double occupancyRate = getOccupancyRate(spot.getSector());
        long minutes = Duration.between(spot.getEntryTime(), exitTime).toMinutes();
        double adjustedPrice = calculateDynamicPrice(spot.getSector().getBasePrice(), occupancyRate);
        double totalPrice = adjustedPrice * Math.ceil(minutes / 60.0);

        return BigDecimal.valueOf(totalPrice);
    }

    private double getOccupancyRate(Sector sector) {
        long occupiedSpots = spotRepository.countSpotsOccupiedBySector(sector.getId());
        return (double) occupiedSpots / sector.getMaxCapacity();
    }

    public double calculateDynamicPrice(double basePrice, double occupancyRate) {
        return switch (getOccupancyCategory(occupancyRate)) {
            case BELOW_25  -> basePrice * BELOW_25.getDiscountRate();
            case BELOW_50  -> basePrice * BELOW_50.getDiscountRate();
            case BELOW_75  -> basePrice * BELOW_75.getDiscountRate();
            case BELOW_100 -> basePrice * BELOW_100.getDiscountRate();
        };
    }

}
