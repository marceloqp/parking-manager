package br.com.mpsolucoes.manager.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.time.OffsetDateTime;

@Data
@ToString
public class EventRequest {

    @NotBlank
    @JsonProperty("license_plate")
    private String licensePlate;

    @NotBlank
    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("entry_time")
    private OffsetDateTime entryTime;

    @JsonProperty("exit_time")
    private OffsetDateTime exitTime;

    private Double lat;

    private Double lng;
}
