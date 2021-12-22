package com.educacional.schoolacademicperiods.infra.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.time.Instant;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeletedRecordEntity {

    private String id;
    private Object record;
    private Instant createdAt;

}