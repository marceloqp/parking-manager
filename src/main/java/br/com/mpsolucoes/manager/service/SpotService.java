package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.SpotStatusRequest;
import br.com.mpsolucoes.manager.domain.dto.response.SpotStatusResponse;
import br.com.mpsolucoes.manager.domain.repository.ParkingRepository;
import br.com.mpsolucoes.manager.domain.repository.SpotRepository;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static br.com.mpsolucoes.manager.configuration.constants.MensagemConstants.SPOT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;

    private final ParkingRepository parkingRepository;

    private final PriceService priceService;

    public SpotStatusResponse get(SpotStatusRequest request) throws RecordNotFoundException {
        var spot =  spotRepository
                .findByLatAndLng(request.getLat(), request.getLng())
                .orElseThrow(() -> new RecordNotFoundException(SPOT_NOT_FOUND));
        var parking = parkingRepository
                .findActualParkingBySpotId(spot.getId());
        if(parking.isPresent()){
            var park = parking.get();
            return SpotStatusResponse
                    .builder()
                    .ocupied(Boolean.TRUE)
                    .licensePlate(park.getLicensePlate())
                    .priceUntilNow(priceService.calculatePrice(spot, LocalDateTime.now()))
                    .entryTime(OffsetDateTime.from(park.getEntryTime()))
                    .timeParked(OffsetDateTime.now()) //TODO avaliar esse retorno
                    .build();
        }
        return SpotStatusResponse
                .builder()
                .ocupied(Boolean.FALSE)
                .licensePlate("")
                .priceUntilNow(BigDecimal.ZERO)
                .entryTime(null)
                .timeParked(null)
                .build();
    }
}
