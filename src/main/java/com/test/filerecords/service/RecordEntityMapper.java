package com.test.filerecords.service;

import com.test.filerecords.persistense.RecordEntity;
import com.test.filerecords.domain.RecordDto;
import org.mapstruct.Mapper;

@Mapper
public interface RecordEntityMapper {

    RecordDto toDto(RecordEntity entity);
    RecordEntity toEntity(RecordDto dto);
}
