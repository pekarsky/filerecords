package com.test.filerecords.service.impl;

import com.test.filerecords.domain.RecordDto;
import com.test.filerecords.service.TimestampParser;
import com.test.filerecords.validation.RecordValidationException;
import com.test.filerecords.validation.RowValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FileParserImplTest {

    private static final String FILE_HEADER = "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP";
    public static final String RECORD_NAME = " kind of name11";
    public static final String RECORD_DESCRIPTION = " kind of description";

    @Mock
    private TimestampParser timestampParser;
    @Mock
    private RowValidator rowValidator;
    @InjectMocks
    private FileParserImpl fileParser;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void shouldParseFileWithHeaderAndRecord() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getInputStream()).thenReturn(
                new ByteArrayInputStream((FILE_HEADER + "\n11," + RECORD_NAME + "," + RECORD_DESCRIPTION + ",123\n\n").getBytes()));
        List<RecordDto> records = fileParser.parseFile(mockMultipartFile);
        assertNotNull(records);
        assertEquals(1, records.size());
        assertEquals(11L, records.get(0).getId());
        assertEquals(RECORD_NAME.trim(), records.get(0).getName());
        assertEquals(RECORD_DESCRIPTION.trim(), records.get(0).getDescription());
    }

    @Test
    void shouldThrowExceptionForWrongHeader() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getInputStream()).thenReturn(
                new ByteArrayInputStream(("NOT REAL HEADER!!!!!\n11," + RECORD_NAME + "," + RECORD_DESCRIPTION + ",123\n\n").getBytes()));

        assertThrows(RecordValidationException.class, () -> fileParser.parseFile(mockMultipartFile),
                "Unexpected value in Header");
    }

    @Test
    void shouldThrowExceptionForEmptyLineInRows() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getInputStream()).thenReturn(
                new ByteArrayInputStream((FILE_HEADER + "\n11," + RECORD_NAME + "," + RECORD_DESCRIPTION + ",123\n\n12,sdf,efsdf\n\n").getBytes()));

        assertThrows(RecordValidationException.class, () -> fileParser.parseFile(mockMultipartFile),
                "Empty line occurred while File has more records");
    }

    @Test
    void shouldThrowExceptionIfFileDontEndsWithEmptyline() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getInputStream()).thenReturn(
                new ByteArrayInputStream((FILE_HEADER + "\n11," + RECORD_NAME + "," + RECORD_DESCRIPTION + ",123\n").getBytes()));

        assertThrows(RecordValidationException.class, () -> fileParser.parseFile(mockMultipartFile),
                "Last line in the file needs to be empty");
    }

    @Test
    void shouldThrowExceptionIfFileIsEmpty() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getInputStream()).thenReturn(
                new ByteArrayInputStream(("").getBytes()));

        assertThrows(RecordValidationException.class, () -> fileParser.parseFile(mockMultipartFile),
                "File is empty");
    }
}