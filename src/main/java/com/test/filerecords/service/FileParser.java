package com.test.filerecords.service;

import com.test.filerecords.domain.RecordDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileParser {
    List<RecordDto> parseFile(MultipartFile file) throws IOException;
}
