package com.test.filerecords.domain;

public class RecordStructure {
    public final static String RECORD_HEADER = "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP";
    public final static int RECORD_LENGTH = 4;

    public final static int ID_POSITION = 0;
    public final static int NAME_POSITION = 1;
    public final static int DESCRIPTION_POSITION = 2;
    public final static int TIMESTAMP_POSITION = 3;

    private RecordStructure() {}
}
