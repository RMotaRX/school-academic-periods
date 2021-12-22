package com.educacional.schoolacademicperiods.domain.model;

import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
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
public class Organization {
    private String id;
    private OrganizationType organizationType;
}
