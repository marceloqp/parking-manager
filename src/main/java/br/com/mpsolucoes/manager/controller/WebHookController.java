package br.com.mpsolucoes.manager.controller;

import br.com.mpsolucoes.manager.domain.dto.request.EventRequest;
import br.com.mpsolucoes.manager.domain.dto.response.RestErrorResponse;
import br.com.mpsolucoes.manager.service.WebHookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "Webhook")
public class WebHookController {

    private final WebHookService webHookService;

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Event accepted", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponse.class))),
                    @ApiResponse(responseCode = "422", description = "", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponse.class))),
            }
    )
    public ResponseEntity<Void> handle(@Parameter(description = "", required = true) @Valid @RequestBody final EventRequest request) {
        webHookService.handle(request);
        return ResponseEntity.accepted().build();
    }
}
