package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.PlateStatusRequest;
import br.com.mpsolucoes.manager.domain.dto.response.PlateStatusResponse;
import br.com.mpsolucoes.manager.domain.repository.ParkingRepository;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static br.com.mpsolucoes.manager.configuration.constants.MensagemConstants.LICENSE_PLATE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlateService {

    private final ParkingRepository parkingRepository;

    private final PriceService priceService;

    public PlateStatusResponse get(PlateStatusRequest request) throws RecordNotFoundException {

        var parking = parkingRepository
                .findActualParkingByPlate(request.getLicensePlate())
                .orElseThrow(() -> new RecordNotFoundException(LICENSE_PLATE_NOT_FOUND));
        return PlateStatusResponse
                .builder()
                .licensePlate(parking.getLicensePlate())
                .timeParked(OffsetDateTime.now()) //TODO avaliar esse retorno
                .entryTime(OffsetDateTime.from(parking.getEntryTime()))
                .priceUntilNow(priceService.calculatePrice(parking.getSpot(), LocalDateTime.now()))
                .lat(parking.getSpot().getLat())
                .lng(parking.getSpot().getLng())
                .build();
    }
}
