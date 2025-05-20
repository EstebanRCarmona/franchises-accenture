package com.accenture.infraestructur.driving_http.mapper;

import com.accenture.domain.model.responses.BranchWithFranchise;
import com.accenture.infraestructur.driving_http.dto.response.BranchWithFranchiseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchWithFranchiseDtoMapper {
    BranchWithFranchiseDtoMapper INSTANCE = Mappers.getMapper(BranchWithFranchiseDtoMapper.class);
    @Mapping(target = "id", source = "branch.id")
    @Mapping(target = "name", source = "branch.name")
    BranchWithFranchiseDto toDto(BranchWithFranchise pair);
}
