package com.educacional.schoolacademicperiods.templates;

import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
import com.educacional.schoolacademicperiods.domain.model.Organization;

import java.time.Instant;
import java.util.List;

public class OrganizationTemplate {
  public static final String FIELD_SCHOOL_ID = "schoolId";
  public static final String FIELD_YEAR = "year";

  public static final String ORGANIZATION_ID = "79c9438f-d486-4fec-8388-f78c5e1fe0f4";
  public static final String SCHOOL_ID = "770f654f-1a03-4cdb-9a14-5ede5cb0c718";
  public static final String TITLE = "Year 2021";
  public static final String YEAR = "2021";
  public static final Instant CREATE_AT = Instant.parse("2021-09-06T17:15:21.514963488Z");
  public static final Instant UPDATE_AT = Instant.parse("2021-09-06T17:15:21.514963488Z");;

  public static Organization getOrganizationTemplate() {
    return new Organization()
        .withId(ORGANIZATION_ID)
        .withOrganizationType(OrganizationType.SCHOOL);
  }

  public static List<Organization> getOrganizationsTemplate() {
    return List.of(getOrganizationTemplate());
  }

}
