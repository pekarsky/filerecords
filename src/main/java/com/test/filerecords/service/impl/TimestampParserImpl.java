package com.test.filerecords.service.impl;

import com.test.filerecords.service.TimestampParser;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TimestampParserImpl implements TimestampParser {
    public Timestamp parse(String timestampString){
        return new Timestamp(Long.parseLong(timestampString)*1000);
    }
}
