package com.test.filerecords.domain;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto {
    private Long id;
    private String name;
    private String description;
    private Date updatedTimestamp;
}
