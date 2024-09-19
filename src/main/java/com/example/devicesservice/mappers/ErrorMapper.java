package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.ErrorResponse;
import com.example.devicesservice.exceptions.BaseException;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ErrorMapper {

    ErrorResponse toErrorResponse(BaseException exception);

}
