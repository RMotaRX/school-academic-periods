package com.educacional.schoolacademicperiods.domain.model;

import java.util.List;

public interface PageDomain<T> {

    List<T> getContent();
    long getTotal();
    long getTotalPages();

}