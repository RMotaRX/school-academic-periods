package com.educacional.schoolacademicperiods.domain.client;

import com.educacional.schoolacademicperiods.domain.model.Organization;

import java.util.List;

public interface OrganizationClient {
  List<Organization> getOrganization(Organization organization);
}
