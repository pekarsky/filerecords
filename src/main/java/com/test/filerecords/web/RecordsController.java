package com.test.filerecords.web;

import com.test.filerecords.domain.RecordDto;
import com.test.filerecords.service.FileParser;
import com.test.filerecords.service.RecordsService;
import com.test.filerecords.service.TimestampParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordsController {

    private final FileParser fileParser;
    private final RecordsService recordsService;
    private final TimestampParser timestampParser;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {

        List<RecordDto> records = fileParser.parseFile(file);
        recordsService.saveRecords(records);

        return ResponseEntity.ok("Records created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recordsService.getRecordById(id));
    }

    @GetMapping()
    public ResponseEntity<List<RecordDto>> getByPeriod(
            @RequestParam("begin") String begin,
            @RequestParam("end") String end,
            @RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(recordsService.getRecords(
                timestampParser.parse(begin),
                timestampParser.parse(end),
                page == null ? 0 : page
        ));
    }
}
