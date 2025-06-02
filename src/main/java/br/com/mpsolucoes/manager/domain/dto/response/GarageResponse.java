package br.com.mpsolucoes.manager.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GarageResponse {
    private List<SectorResponse> garage;
    private List<SpotResponse> spots;
}
