package com.educacional.schoolacademicperiods.domain.model;

import com.educacional.schoolacademicperiods.application.rest.validator.IsUuid;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AcademicPeriodQueryFilter extends QueryFilter {

    @IsUuid
    private String periodId;

    @IsUuid
    private String yearId;

    @IsUuid
    private String status;

    private Instant createdAtStart;

    private Instant createdAtEnd;

}
