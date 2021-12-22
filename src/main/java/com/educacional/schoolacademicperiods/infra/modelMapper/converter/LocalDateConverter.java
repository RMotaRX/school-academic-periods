package com.educacional.schoolacademicperiods.infra.modelMapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
        return mappingContext.getSource() != null ?
                LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;

    }
}