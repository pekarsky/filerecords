package com.test.filerecords.web;

import com.test.filerecords.validation.RecordValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(RecordValidationException.class)
    protected ResponseEntity<Object> handleFileValidationException(
            RecordValidationException ex) {
        logger.error(ex.getMessage() + "\n" + ex.getRecordString());
        return ResponseEntity.badRequest().body(ex.getMessage() + "\n" + ex.getRecordString());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body("Unexpected Exception. Contact support");
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.notFound().build();
    }
}

