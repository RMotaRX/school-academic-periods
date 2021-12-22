package com.educacional.schoolacademicperiods.application.rest.request;

import com.educacional.schoolacademicperiods.application.rest.validator.DateValidator;
import com.educacional.schoolacademicperiods.application.rest.validator.IsUuid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UpdateSchoolAcademicPeriodRequest {
    @Schema(required = true, type = "string($uuid)", example = "example: 838489f9-c288-4ec8-9008-e2a8a62b2869")
    @NotBlank
    @IsUuid
    private String yearId;

    @Schema(type = "string", example = "Period of the 2021")
    private String title;

    @Schema(type = "string($date)", example = "2021-1-31")
    @NotBlank
    @DateValidator
    private String startDate;

    @Schema(type = "string($date)", example = "2021-12-31")
    @NotBlank
    @DateValidator
    private String endDate;

}
