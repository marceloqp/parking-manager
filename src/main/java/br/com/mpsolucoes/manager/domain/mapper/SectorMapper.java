package br.com.mpsolucoes.manager.domain.mapper;

import br.com.mpsolucoes.manager.domain.dto.response.SectorResponse;
import br.com.mpsolucoes.manager.domain.entity.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface SectorMapper {

    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    @Mapping(source = "sectorName", target = "sector")
    @Mapping(source = "maxParkingTime", target = "durationLimitMinutes")
    SectorResponse mapToResponse(Sector entity);

    List<SectorResponse> mapToListResponse(List<Sector> entities);
}
