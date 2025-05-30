package br.com.mpsolucoes.manager.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ParkingResponse {
    private List<GarageResponse> garage;
    private List<SpotResponse> spots;
}
