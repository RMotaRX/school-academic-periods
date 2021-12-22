package com.educacional.schoolacademicperiods.application.rest.request;

import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import com.educacional.schoolacademicperiods.application.rest.validator.IsUuid;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AcademicPeriodQueryFilterRequest extends QueryFilterRequest {


    @Parameter(
            description = "Period identifier",
            schema = @Schema(type = "String", format = "UUID"))
    @IsUuid
    private String periodId;
    @Parameter(
            description = "Year identifier",
            schema = @Schema(type = "String", format = "UUID"))
    @IsUuid
    private String yearId;
    @Parameter(
            description = "Status",
            schema = @Schema(implementation = Status.class))
    private String status;

    @Schema(
            description = "Create At Start",
            example = "2021-11-30T11:01:44.203Z")
    private Instant createdAtStart;
    @Schema(
            description = "Create At End",
            format = "UUID",
            example = "2022-11-30T11:01:44.203Z")
    private Instant createdAtEnd;

}
