package com.test.filerecords.service;

import com.test.filerecords.domain.RecordDto;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface RecordsService {

    void saveRecords(List<RecordDto> dtoList);

    @Transactional(readOnly = true)
    RecordDto getRecordById(Long id);

    @Transactional(readOnly = true)
    List<RecordDto> getRecords(Timestamp begin, Timestamp end, int page);
}
