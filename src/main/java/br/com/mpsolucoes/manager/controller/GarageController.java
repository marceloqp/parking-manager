package br.com.mpsolucoes.manager.controller;

import br.com.mpsolucoes.manager.domain.dto.response.GarageResponse;
import br.com.mpsolucoes.manager.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Garage")
@RestController
@RequiredArgsConstructor
public class GarageController {

    private final GarageService garageService;

    @GetMapping(path = "/garage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Busca as informações de garagem",
            description = "Retorna as informações de garagem e abre a cancela para a entrada de veículos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Garagem encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GarageResponse.class)))
            }
    )
    public ResponseEntity<GarageResponse> get() {
        return ResponseEntity
                .ok()
                .body(garageService.get());
    }
}
