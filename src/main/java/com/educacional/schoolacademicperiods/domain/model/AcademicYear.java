package com.educacional.schoolacademicperiods.domain.model;

import com.educacional.schoolacademicperiods.domain.enumeration.DatabaseIndexes;
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
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(name = DatabaseIndexes.Constants.SCHOOL_YEAR, def = "{'schoolId' : 1, 'year': 1}", unique = true)
public class AcademicYear {

  @Id
  private String _id;

  private String id;

  private String schoolId;

  private String title;

  @NotBlank
  private String year;

  private Status status;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;
}
