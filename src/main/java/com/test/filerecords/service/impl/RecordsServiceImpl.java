package com.test.filerecords.service.impl;

import com.test.filerecords.domain.RecordDto;
import com.test.filerecords.persistense.RecordRepository;
import com.test.filerecords.service.RecordEntityMapper;
import com.test.filerecords.service.RecordsService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
public class RecordsServiceImpl implements RecordsService {

    private final RecordRepository recordRepository;
    private final RecordEntityMapper mapper;

    @Value("${filerecords.page.size:10}")
    private int pageSize;

    @Override
    public void saveRecords(List<RecordDto> dtoList) {
        for(RecordDto dto: dtoList){
            recordRepository.save(mapper.toEntity(dto));
        }
    }

    @Override
    public RecordDto getRecordById(Long id) {
        return recordRepository.findById(id).map(mapper::toDto).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<RecordDto> getRecords(Timestamp begin, Timestamp end, int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("updatedTimestamp"));
        return recordRepository
                .findAllByUpdatedTimestampBetween(begin, end, pageable)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
