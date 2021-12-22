package com.educacional.schoolacademicperiods.infra.client.impl;

import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.model.Organization;
import com.educacional.schoolacademicperiods.infra.client.OrganizationFeignClient;
import com.educacional.schoolacademicperiods.infra.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationClientImpl implements OrganizationClient {

  private final OrganizationFeignClient organizationFeignClient;
  private final OrganizationMapper organizationMapper;

  @Override
  public List<Organization> getOrganization(Organization organization) {
    return organizationFeignClient.getOrganization(
        organizationMapper.toOrganizationParameters(organization));
  }
}
