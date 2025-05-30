package br.com.mpsolucoes.manager.service;

import br.com.mpsolucoes.manager.domain.dto.request.RevenueRequest;
import br.com.mpsolucoes.manager.domain.dto.response.RevenueResponse;
import br.com.mpsolucoes.manager.domain.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final ParkingRepository parkingRepository;

    private static final String CURRENCY = "BRL"; //TODO change to param

    public RevenueResponse get(RevenueRequest request) {

        LocalDateTime start = request.getDate().atStartOfDay();
        LocalDateTime end = request.getDate().atTime(LocalTime.MAX);
        var totalAmount = parkingRepository.findTotalRevenueBySectorAndDate(request.getSector(), start, end);

        return RevenueResponse
                .builder()
                .amount(totalAmount)
                .currency(CURRENCY)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
