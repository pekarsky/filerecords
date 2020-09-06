package com.test.filerecords.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordsController {

    @PostMapping
    public ResponseEntity<String> upload(){
        return ResponseEntity.ok("Records created: ");//TODO: add records count?
    }
}
