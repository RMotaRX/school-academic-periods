package com.educacional.schoolacademicperiods.application.rest.request;

import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import com.educacional.schoolacademicperiods.application.rest.validator.StringEnumeration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAcademicStatusRequest {
    @StringEnumeration(enumClass = Status.class)
    @NotNull
    private String status;
}
