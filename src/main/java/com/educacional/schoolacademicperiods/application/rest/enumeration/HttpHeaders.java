package com.educacional.schoolacademicperiods.application.rest.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpHeaders {

    ACCEPT_RANGES("Accept-Ranges"), CONTENT_RANGE("Content-Range"), TOTAL_PAGES("Total-Pages");

    public final String name;
}
