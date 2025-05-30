package br.com.mpsolucoes.manager.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpotStatusRequest {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

}
