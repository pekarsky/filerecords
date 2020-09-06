package com.test.filerecords.service;

import com.test.filerecords.domain.RecordDto;
import com.test.filerecords.persistense.RecordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class RecordEntityMapperTest {

    private RecordEntityMapper mapper;

    @BeforeEach
    public void init(){
        mapper = Mappers.getMapper(RecordEntityMapper.class);
    }

    @Test
    void testToDtoMapping() {
        RecordEntity entity = new RecordEntity(11L, "name", "description", new Timestamp(123456L));
        RecordDto dto = mapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getUpdatedTimestamp(), dto.getUpdatedTimestamp());
    }

    @Test
    void testToEntityMapping() {
        RecordDto dto = new RecordDto(11L, "name", "description", new Timestamp(123456L));
        RecordEntity entity = mapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getUpdatedTimestamp(), entity.getUpdatedTimestamp());
    }
}