package br.com.mpsolucoes.manager.controller;

import br.com.mpsolucoes.manager.domain.dto.request.RevenueRequest;
import br.com.mpsolucoes.manager.domain.dto.response.RevenueResponse;
import br.com.mpsolucoes.manager.service.RevenueService;
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

@Tag(name = "Revenue")
@RestController
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @PostMapping(path = "/revenue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Informações de faturamento",
            description = "Permite buscar o faturamento por setor e data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Faturamento encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RevenueResponse.class)))
            }
    )
    public ResponseEntity<RevenueResponse> get(@RequestBody @Valid RevenueRequest request) {
        return ResponseEntity
                .ok()
                .body(revenueService.get(request));
    }
}
