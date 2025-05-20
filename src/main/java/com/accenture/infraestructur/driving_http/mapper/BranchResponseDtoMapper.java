package com.accenture.infraestructur.driving_http.mapper;

import com.accenture.domain.model.Branch;
import com.accenture.infraestructur.driving_http.dto.response.BranchResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchResponseDtoMapper {
    BranchResponseDto toDto(Branch branch);
}
