package com.educacional.schoolacademicperiods.application.rest.request;

import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import com.educacional.schoolacademicperiods.application.rest.validator.IsUuid;
import com.educacional.schoolacademicperiods.application.rest.validator.StringEnumeration;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
public class AcademicYearQueryFilterRequest extends QueryFilterRequest{

    @IsUuid
    @Parameter(
            description = "Identifier of the academic year",
            schema = @Schema(type = "string", format = "uuid", defaultValue = "838489f9-c288-4ec8-9008-e2a8a62b2869"))
    private String id;

    @Parameter(
            description = "Filtering by Academic year",
            schema = @Schema(type = "string", format = "int32", defaultValue = "2021"))
    @Size(min = 4, max = 4)
    private String year;

    @StringEnumeration(enumClass = Status.class)
    @Parameter(
            description = "Status",
            schema = @Schema( defaultValue = "ACTIVE",  implementation = Status.class,enumAsRef = true))
    private String status;

    @Parameter(
            description = "Filtering by start date of record creation",
            schema = @Schema(type = "string", format = "date-time"))
    private Instant createdAtStart;

    @Parameter(
            description = "Filtering by end date of record creation",
            schema = @Schema(type = "string", format = "date-time"))
    private Instant createdAtEnd;

}
