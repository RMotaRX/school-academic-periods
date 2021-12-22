package com.educacional.schoolacademicperiods.infra.client;

import com.educacional.schoolacademicperiods.domain.model.Organization;
import com.educacional.schoolacademicperiods.infra.client.parameters.OrganizationParameters;
import com.educacional.schoolacademicperiods.infra.configuration.OrganizationFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
    name = "organization",
    url = "${rest.url.ms.organizations}",
    configuration = {OrganizationFeignConfiguration.class})
public interface OrganizationFeignClient {

  @GetMapping("/v1/organizations/")
  List<Organization> getOrganization(@SpringQueryMap OrganizationParameters organizationParameters);
}
