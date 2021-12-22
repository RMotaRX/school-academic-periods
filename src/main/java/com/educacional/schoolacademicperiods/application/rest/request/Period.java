package com.educacional.schoolacademicperiods.application.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Period {
    @Schema(type = "string($date)",required = true)
    @NotNull
    private LocalDate startDate;
    @Schema(type = "string($date)",required = true)
    @NotNull
    private LocalDate endDate;
}
