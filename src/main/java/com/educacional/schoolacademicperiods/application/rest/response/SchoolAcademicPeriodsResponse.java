package com.educacional.schoolacademicperiods.application.rest.response;

import com.educacional.schoolacademicperiods.application.rest.enumerations.PeriodType;
import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import com.educacional.schoolacademicperiods.application.rest.validator.StringEnumeration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
public class SchoolAcademicPeriodsResponse {
    @Schema(type="string($uuid)",accessMode = Schema.AccessMode.READ_ONLY,example = "838489f9-c288-4ec8-9008-e2a8a62b2869")
    private String id;

    @Schema(type="string($uuid)",accessMode = Schema.AccessMode.READ_ONLY,example = "838489f9-c288-4ec8-9008-e2a8a62b2869")
    private String schoolId;

    @Schema(type="string($uuid)",accessMode = Schema.AccessMode.READ_ONLY,example = "838489f9-c288-4ec8-9008-e2a8a62b2869")
    private String yearId;

    @Schema(type = "string", example = "Period of 2021")
    private String title;

    private Period period;

    @Schema(type="string($enum)",accessMode = Schema.AccessMode.READ_ONLY)
    @StringEnumeration(enumClass = PeriodType.class)
    private String type;

    @Schema(type="string($enum)",accessMode = Schema.AccessMode.READ_ONLY)
    @StringEnumeration(enumClass = Status.class)
    private String status;

    @Schema(type="string($date-time)",accessMode = Schema.AccessMode.READ_ONLY)
    private Instant createdAt;


    @Schema(type="string($date-time)",accessMode = Schema.AccessMode.READ_ONLY)
    private Instant updateAt;

}
