package br.com.mpsolucoes.manager.domain.dto.response;

import lombok.Data;

@Data
public class SpotResponse {
    private Integer id;
    private String sector;
    private Double lat;
    private Double lng;
}
