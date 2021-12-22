package com.educacional.schoolacademicperiods.domain.model;


import com.educacional.schoolacademicperiods.domain.enumeration.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class QueryFilter {
    private int limit;
    private int page;

    private SortType sortType;
    private String sort;
}

