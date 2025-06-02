package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.response.GarageResponse;
import br.com.mpsolucoes.manager.domain.entity.GarageStatus;
import br.com.mpsolucoes.manager.domain.entity.Sector;
import br.com.mpsolucoes.manager.domain.entity.Spot;
import br.com.mpsolucoes.manager.domain.mapper.SectorMapper;
import br.com.mpsolucoes.manager.domain.mapper.SpotMapper;
import br.com.mpsolucoes.manager.domain.repository.GarageStatusRepository;
import br.com.mpsolucoes.manager.domain.repository.SectorRepository;
import br.com.mpsolucoes.manager.domain.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageService {

    private final GarageStatusRepository garageStatusRepository;

    private final SpotRepository spotRepository;

    private final SectorRepository sectorRepository;

    public GarageResponse get() {

        var status = getGarageStatus();
        if (Boolean.FALSE.equals(status.getStatus())) {
            status.setStatus(Boolean.TRUE);
            garageStatusRepository.save(status);
        } else {
            garageStatusRepository.save(new GarageStatus(Boolean.TRUE));
        }
        List<Sector> sectors = sectorRepository.findAll();
        List<Spot> spots = spotRepository.findAll();

        return GarageResponse
                .builder()
                .garage(SectorMapper.INSTANCE.mapToListResponse(sectors))
                .spots(SpotMapper.INSTANCE.mapToListResponse(spots))
                .build();
    }

    public GarageStatus getGarageStatus() {
        var status = garageStatusRepository.findAll();
        if (status.iterator().hasNext()) {
            return status.iterator().next();
        }
        return new GarageStatus(Boolean.FALSE);
    }
}
