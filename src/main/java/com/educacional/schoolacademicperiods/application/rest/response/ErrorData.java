package com.educacional.schoolacademicperiods.application.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorData {
    private String field;
    private String message;
    private Object value;
}
