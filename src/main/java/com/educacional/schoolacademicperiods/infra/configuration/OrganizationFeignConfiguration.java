package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.infra.feign.OrganizationFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationFeignConfiguration {
    @Bean
    public OrganizationFeignErrorDecoder suitesFeignOrganizationErrorDecoder() {
        return new OrganizationFeignErrorDecoder();
    }
}
