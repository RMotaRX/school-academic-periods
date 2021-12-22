package com.educacional.schoolacademicperiods.application.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Period {

    @Schema(type = "string($date)",required = true)
    @NotNull
    private LocalDate startDate;

    @Schema(type = "string($date)",required = true)
    @NotNull
    private LocalDate endDate;
}
