package com.educacional.schoolacademicperiods.application.rest.response;

import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcademicYearResponse {

  private String id;

  private String schoolId;

  @Schema(
          description = "Title to represent the academic year",
          example = "Year 2021")
  private String title;

  @NotBlank
  @Schema(
      description = "Academic year",
      required = true,
      format = "date-fullyear",
      pattern = "YYYY",
      example = "2021")
  @Size(min = 4, max = 4)
  @Pattern(regexp = "[0-9]*")
  private String year;

  private Status status;
  private Instant createdAt;
  private Instant updatedAt;

}
