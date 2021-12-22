package com.educacional.schoolacademicperiods.application.rest.request;

import com.educacional.schoolacademicperiods.application.rest.validator.StringEnumeration;
import com.educacional.schoolacademicperiods.domain.enumeration.SortField;
import com.educacional.schoolacademicperiods.domain.enumeration.SortType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class QueryFilterRequest {

    @Parameter(
            description = "Number of the page where pagination starts.",
            schema = @Schema(type = "integer", format = "int32", minimum = "1", defaultValue = "1"))
    private int page = 1;

    @Parameter(
            description = "Limit results per page.",
            schema = @Schema(type = "integer", format = "int32", minimum = "1", maximum = "30", defaultValue = "10"))
    @Max(30)
    @Min(1)
    private int limit = 10;

    @Parameter(
            description = "Field to perform the sorting.",
            schema = @Schema(implementation = SortField.class ,defaultValue = "createdAt"))
    @StringEnumeration(enumClass = SortField.class)
    private String sort = "createdAt";

    @Parameter(
            description = "Sorting type in search.",
            schema = @Schema(implementation = SortType.class, defaultValue = "DESC"))
    @StringEnumeration(enumClass = SortType.class)
    private String sortType = "DESC";
}

