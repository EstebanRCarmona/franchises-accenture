package com.accenture.infraestructur.driving_http.mapper;

import org.mapstruct.Mapper;

import com.accenture.domain.model.Franchise;
import com.accenture.infraestructur.driving_http.dto.response.FranchiseResponseDto;

@Mapper(componentModel = "spring")
public interface FranchiseResponseDtoMapper {
    FranchiseResponseDto toDto(Franchise franchise);
}
