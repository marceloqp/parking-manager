package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.EventRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebHookService {

    public void handle(@Valid EventRequest request) {
        log.info("Recebido request: {}", request);
    }
}
