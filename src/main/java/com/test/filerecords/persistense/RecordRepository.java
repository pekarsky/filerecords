package com.test.filerecords.persistense;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.stream.Stream;

@Repository
public interface RecordRepository extends PagingAndSortingRepository<RecordEntity, Long> {
    //TODO: sorting order was not specified in requirements
    Stream<RecordEntity> findAllByUpdatedTimestampBetween(Timestamp begin, Timestamp end, Pageable pageable);
}
