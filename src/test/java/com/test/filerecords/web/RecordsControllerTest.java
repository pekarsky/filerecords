package com.test.filerecords.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RecordsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldUpload1RecordFileAndSuccessfullyGetRecordAnd404OnWrongId() throws Exception {
        FileInputStream fis = new FileInputStream("src/test/resources/testfiles/testFile_success_one_record.txt");
        MockMultipartFile upload = new MockMultipartFile("file", fis);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/")
                        .file(upload);
        mockMvc.perform(builder).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.get("/111")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/123456")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldThrowExceptionOnWrongFileAndNoRecordsSaved() throws Exception {
        FileInputStream fis = new FileInputStream("src/test/resources/testfiles/testFile_error_empty_line.txt");
        MockMultipartFile upload = new MockMultipartFile("file", fis);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/")
                        .file(upload);
        mockMvc.perform(builder).andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.get("/1000")).andExpect(status().isNotFound());
    }

}