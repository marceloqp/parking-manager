package br.com.mpsolucoes.manager.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectorResponse {

    private String sector;
    @JsonProperty("basePrice")
    private Double basePrice;
    @JsonProperty("max_capacity")
    private Integer maxCapacity;
    @JsonProperty("open_hour")
    private String openHour;
    @JsonProperty("close_hour")
    private String closeHour;
    @JsonProperty("duration_limit_minutes")
    private Integer durationLimitMinutes;
}
