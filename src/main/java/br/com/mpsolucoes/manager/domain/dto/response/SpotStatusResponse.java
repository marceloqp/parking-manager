package br.com.mpsolucoes.manager.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class SpotStatusResponse {

    private boolean ocupied;

    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("price_until_now")
    private BigDecimal priceUntilNow;

    @JsonProperty("entry_time")
    private OffsetDateTime entryTime;

    @JsonProperty("time_parked")
    private OffsetDateTime timeParked;

}
