package com.test.filerecords.service.impl;

import com.opencsv.CSVParser;
import com.test.filerecords.domain.RecordDto;
import com.test.filerecords.service.FileParser;
import com.test.filerecords.service.TimestampParser;
import com.test.filerecords.validation.RecordValidationException;
import com.test.filerecords.validation.RowValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.test.filerecords.domain.RecordStructure.*;

@Component
@RequiredArgsConstructor
public class FileParserImpl implements FileParser {

    private final TimestampParser timestampParser;
    private final RowValidator rowValidator;

    @Override
    public List<RecordDto> parseFile(MultipartFile file) throws IOException {
        List<RecordDto> result = new ArrayList<>();
        CSVParser csvParser = new CSVParser();
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String header = br.readLine();
        if (header == null) {
            throw new RecordValidationException("File is empty", "");
        }
        if (!RECORD_HEADER.equals(header)) {
            throw new RecordValidationException("Unexpected value in Header", header);
        }
        boolean emptyLineFound = false;
        String line;
        while ((line = br.readLine()) != null) {
            if (Strings.isEmpty(line)) {
                emptyLineFound = true;
            } else {
                if(emptyLineFound){
                    throw new RecordValidationException("Empty line occurred while File has more records", line);
                }
                result.add(toDto(csvParser.parseLine(line), line));
            }
        }
        if (!result.isEmpty() && !emptyLineFound) {
            throw new RecordValidationException("Last line in the file needs to be empty", line);
        }
        return result;
    }

    private RecordDto toDto(String[] array, String line) {
        rowValidator.validateRow(array, line);
        return RecordDto.builder()
                .id(Long.valueOf(array[ID_POSITION].trim()))
                .name(array[NAME_POSITION].trim())
                .description(array[DESCRIPTION_POSITION].trim())
                .updatedTimestamp(timestampParser.parse(array[TIMESTAMP_POSITION].trim()))
                .build();
    }
}
