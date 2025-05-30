package br.com.mpsolucoes.manager.domain.enums;

import lombok.Getter;

@Getter
public enum OccupancyCategory {
    BELOW_25(0.25, 0.9),
    BELOW_50(0.50, 0),
    BELOW_75(0.75, 1.1),
    BELOW_100(1.0, 1.25);

    private final double occupancyRate;
    private final double discountRate;

    OccupancyCategory(double occupancyRate, double discountRate) {
        this.occupancyRate = occupancyRate;
        this.discountRate = discountRate;
    }

    public static OccupancyCategory getOccupancyCategory(double occupancyRate) {
        if (occupancyRate < BELOW_25.occupancyRate) return OccupancyCategory.BELOW_25;
        if (occupancyRate < BELOW_50.occupancyRate) return OccupancyCategory.BELOW_50;
        if (occupancyRate < BELOW_75.occupancyRate) return OccupancyCategory.BELOW_75;
        return OccupancyCategory.BELOW_100;
    }

}
