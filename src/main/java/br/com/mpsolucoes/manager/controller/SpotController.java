package br.com.mpsolucoes.manager.controller;

import br.com.mpsolucoes.manager.domain.dto.request.SpotStatusRequest;
import br.com.mpsolucoes.manager.domain.dto.response.RestErrorResponse;
import br.com.mpsolucoes.manager.domain.dto.response.SpotStatusResponse;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import br.com.mpsolucoes.manager.service.SpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Spot")
@RestController
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @GetMapping(path = "/spot-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Busca o status de uma vaga",
            description = "Permite buscar o status de uma vaga usando latitude e longitude",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SpotStatusResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não encontrada.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponse.class)))
            }
    )
    public ResponseEntity<SpotStatusResponse> get(@ParameterObject @Valid SpotStatusRequest request) throws RecordNotFoundException {
        return ResponseEntity
                .ok()
                .body(spotService.get(request));
    }
}
