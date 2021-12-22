package com.educacional.schoolacademicperiods.infra.client;

import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.infra.client.parameters.SchoolClassQueryParams;
import com.educacional.schoolacademicperiods.infra.configuration.SchoolClassFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="school-class", url = "${rest.url.ms.school-class}",configuration = {SchoolClassFeignConfiguration.class})
public interface SchoolClassFeignClient {
    @GetMapping("/v1/school-class/{schoolId}/class")
    List<SchoolClass> getSchoolClass(@PathVariable String schoolId, @SpringQueryMap SchoolClassQueryParams schoolClassQueryParams);
}
