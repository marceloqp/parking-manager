package br.com.mpsolucoes.manager.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlateStatusRequest {

    @NotBlank
    @JsonProperty("license_plate")
    private String licensePlate;

}
