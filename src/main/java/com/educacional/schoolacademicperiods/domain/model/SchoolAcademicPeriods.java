package com.educacional.schoolacademicperiods.domain.model;

import com.educacional.schoolacademicperiods.application.rest.validator.IsUuid;
import com.educacional.schoolacademicperiods.domain.enumeration.PeriodType;
import com.educacional.schoolacademicperiods.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(name = "schoolId_yearId_period", def = "{'schoolId' : 1, 'yearId': 1,'period': 1}", unique = true)
public class SchoolAcademicPeriods {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String id;

    @NotBlank
    @IsUuid
    private String schoolId;

    @NotBlank
    @IsUuid
    private String yearId;

    private String title;

    @NotNull
    private Period period;

    private PeriodType type;

    private Status status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updateAt;
}
