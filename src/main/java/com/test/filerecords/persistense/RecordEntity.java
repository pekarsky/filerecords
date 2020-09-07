package com.test.filerecords.persistense;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Timestamp updatedTimestamp;
}
