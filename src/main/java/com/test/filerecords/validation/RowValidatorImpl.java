package com.test.filerecords.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.test.filerecords.domain.RecordStructure.*;

@Component
public class RowValidatorImpl implements RowValidator {
    @Override
    public void validateRow(String[] row, String line) {
        if (row.length != RECORD_LENGTH) {
            throw new RecordValidationException("Record length differs from expected", line);
        }
        if(row[ID_POSITION].isBlank()){
            throw new RecordValidationException("ID cannot be blank", row[ID_POSITION]);
        }
        if(!StringUtils.isNumeric(row[ID_POSITION].trim())){
            throw new RecordValidationException("ID can be numeric only", row[ID_POSITION]);
        }
        if(!StringUtils.isNumeric(row[TIMESTAMP_POSITION].trim())){
            throw new RecordValidationException("Timestamp can be numeric unix Timestamp only", row[TIMESTAMP_POSITION]);
        }
    }
}
