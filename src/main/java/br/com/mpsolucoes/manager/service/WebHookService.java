package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.EventRequest;
import br.com.mpsolucoes.manager.domain.entity.Parking;
import br.com.mpsolucoes.manager.domain.entity.Sector;
import br.com.mpsolucoes.manager.domain.repository.ParkingRepository;
import br.com.mpsolucoes.manager.domain.repository.SectorRepository;
import br.com.mpsolucoes.manager.domain.repository.SpotRepository;
import br.com.mpsolucoes.manager.exception.BusinessException;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebHookService {

    private final GarageService garageService;

    private final PriceService priceService;

    private final SpotRepository spotRepository;

    private final ParkingRepository parkingRepository;

    private final SectorRepository sectorRepository;

    public void handle(EventRequest request) throws BusinessException {
        log.info("Recebido request: {}", request);

        if (Boolean.FALSE.equals(garageService.getGarageStatus().getStatus())) {
            throw new BusinessException("Garagem não aberta!");
        }

        switch (request.getEventType()) {
            case "ENTRY" -> processEntryEvent(request);
            case "PARKED" -> processParkedEvent(request);
            case "EXIT" -> processExitEvent(request);
            default -> throw new BusinessException("Tipo de evento inválido: " + request.getEventType());

        }

    }

    private void processExitEvent(EventRequest request) throws BusinessException {

        if (request.getEntryTime() == null) {
            throw new BusinessException("Horário de saída é obrigatório.");
        }

        var parking = parkingRepository.findActualParkingByPlate(request.getLicensePlate())
                .orElseThrow(() ->
                        new RecordNotFoundException("Nenhum registro de estacionamento encontrado para a placa: " + request.getLicensePlate()));

        parking.setExitTime(request.getExitTime().toLocalDateTime());

        BigDecimal price = priceService.calculatePrice(parking.getSpot(), request.getExitTime().toLocalDateTime());
        Integer parkingTime = Math.toIntExact(Duration.between(parking.getEntryTime(), parking.getExitTime()).toMinutes());
        parking.setAmount(price);
        parking.setParkingTime(parkingTime);
        parkingRepository.save(parking);

    }

    private void processParkedEvent(EventRequest request) throws BusinessException {
        var spot = spotRepository.findByLatAndLng(request.getLat(), request.getLng())
                .orElseThrow(() -> new RecordNotFoundException("Vaga não encontrada."));

        if (spot.isOccupied()) {
            throw new BusinessException("Vaga já ocupada");
        }
        spot.setOccupied(Boolean.TRUE);
        spot.setLicensePlate(request.getLicensePlate());
        spot.setEntryTime(request.getEntryTime().toLocalDateTime());
        spotRepository.save(spot);
    }

    private void processEntryEvent(EventRequest request) throws BusinessException {

        if (request.getEntryTime() == null) {
            throw new BusinessException("Horário de entrada é obrigatório.");
        }

        var sectors = sectorRepository
                .findAll();
        var openHour = sectors
                .stream()
                .min(Comparator.comparing(Sector::getOpenHour));
        var closeHour = sectors
                .stream()
                .max(Comparator.comparing(Sector::getCloseHour));

        if (!validateBusinessHours(openHour.get().getOpenHour(), closeHour.get().getCloseHour(), request.getEntryTime().toLocalTime())) {
            throw new BusinessException("Entrada fora do horário de funcionamento");
        }
        var parking = Parking
                .builder()
                .entryTime(request.getEntryTime().toLocalDateTime())
                .licensePlate(request.getLicensePlate())
                .build();

        parkingRepository.save(parking);
    }

    private boolean validateBusinessHours(LocalTime openHour, LocalTime closeHour, LocalTime time) {
        return (openHour.isBefore(time) && closeHour.isAfter(time));
    }
}
