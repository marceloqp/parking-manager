package br.com.mpsolucoes.manager.controller;

import br.com.mpsolucoes.manager.domain.dto.request.PlateStatusRequest;
import br.com.mpsolucoes.manager.domain.dto.response.PlateStatusResponse;
import br.com.mpsolucoes.manager.domain.dto.response.RestErrorResponse;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import br.com.mpsolucoes.manager.service.PlateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Plate")
@RestController
@RequiredArgsConstructor
public class PlateController {

    private final PlateService plateService;

    @PostMapping(path = "/plate-status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Busca o status de uma placa",
            description = "Permite buscar o status de uma placa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PlateStatusResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Placa não encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponse.class))),
            }
    )
    public ResponseEntity<PlateStatusResponse> get(@RequestBody @Valid PlateStatusRequest request) throws RecordNotFoundException {
        return ResponseEntity
                .ok()
                .body(plateService.get(request));
    }
}
