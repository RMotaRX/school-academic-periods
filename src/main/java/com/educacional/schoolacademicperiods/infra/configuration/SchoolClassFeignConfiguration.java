package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.infra.feign.SchoolClassFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchoolClassFeignConfiguration {
    @Bean
    public SchoolClassFeignErrorDecoder schoolAcademicPeriodsFeignSchoolClassErrorDecoder() {
        return new SchoolClassFeignErrorDecoder();
    }
}
