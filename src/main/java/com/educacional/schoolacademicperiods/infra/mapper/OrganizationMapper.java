package com.educacional.schoolacademicperiods.infra.mapper;

import com.educacional.schoolacademicperiods.domain.model.Organization;
import com.educacional.schoolacademicperiods.infra.client.parameters.OrganizationParameters;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationMapper {
  private final ModelMapper modelMapper;

  public OrganizationParameters toOrganizationParameters(Organization organization) {
    return modelMapper.map(organization, OrganizationParameters.class);
  }
}
