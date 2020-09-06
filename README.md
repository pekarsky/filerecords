FileRecord test project

Assumptions:
 - Timestamp format was not defined, so I used unix timestamp
    (TimestampParser is extracted to separate class, so it is possible to implement other formats support)
 - PRIMARY_KEY format was not defined too, so numeric (Long) is used 
 - File with empty line inside other rows is treated like invalid
 - Sorting order and attribute for pageable retrieval was not specified, by Timestamp used
 - Fields in rows could be quoted and/or escaped
 - File is not validated separately to avoid time and memory consumption. Any failure leads to transaction rollback.
 
 APIs:
 - POST / - upload file (file attribute name is "file")
 - GET /{id} - get by id
 - GET / - get by timestamp. parameters name are - "begin", "end", format - unix timestamp. "page" is optional.
 
 Two sample files are in /src/test/resources/testfiles together with simple Postman collection