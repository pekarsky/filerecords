package com.test.filerecords.service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public interface TimestampParser {
    Timestamp parse(String timestampString);
}
