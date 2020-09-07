package com.test.filerecords.validation;

import lombok.Getter;

@Getter
public class RecordValidationException extends RuntimeException {

    public RecordValidationException(String message, String recordString) {
        super(message);
        this.recordString = recordString;
    }

    private final String recordString;
}
