package br.com.mpsolucoes.manager.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueRequest {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("sector")
    private String sector;
}
