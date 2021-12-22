package com.educacional.schoolacademicperiods.domain.enumeration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseIndexes {
  SCHOOL_YEAR(Constants.SCHOOL_YEAR);

  public final String value;

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {
    public static final String SCHOOL_YEAR = "schoolId_Year";
  }
}
