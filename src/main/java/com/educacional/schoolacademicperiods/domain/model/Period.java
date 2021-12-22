package com.educacional.schoolacademicperiods.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Setter
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Period {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
