package br.com.mpsolucoes.manager.domain.mapper;

import br.com.mpsolucoes.manager.domain.dto.response.SpotResponse;
import br.com.mpsolucoes.manager.domain.entity.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface SpotMapper {

    SpotMapper INSTANCE = Mappers.getMapper( SpotMapper.class );

    @Mapping(source = "sector.sectorName", target = "sector")
    SpotResponse mapToResponse(Spot entity);

    List<SpotResponse> mapToListResponse(List<Spot> entities);
}
