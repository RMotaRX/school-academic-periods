package com.educacional.schoolacademicperiods.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationType {
    SCHOOL("School"), PUBLIC_NETWORK("Public Network"), PRIVATE_NETWORK("Private Network");

    private final String value;

}
