package com.educacional.schoolacademicperiods.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.time.Instant;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class AcademicYearQueryFilter extends QueryFilter {

    public String schoolId;

    public String id;

    public String year;

    private String status;

    private Instant createdAtStart;

    private Instant createdAtEnd;

}
