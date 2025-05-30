package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.SpotStatusRequest;
import br.com.mpsolucoes.manager.domain.dto.response.SpotResponse;
import br.com.mpsolucoes.manager.domain.mapper.SpotMapper;
import br.com.mpsolucoes.manager.domain.repository.SpotRepository;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.mpsolucoes.manager.configuration.constants.MensagemConstants.SPOT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;

    public SpotResponse get(SpotStatusRequest request) throws RecordNotFoundException {
        return spotRepository
                .findByLatAndLng(request.getLat(), request.getLng())
                .map(SpotMapper.INSTANCE::mapToResponse)
                .orElseThrow(() -> new RecordNotFoundException(SPOT_NOT_FOUND));
    }
}
