package com.educacional.schoolacademicperiods.templates;

import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateYearStatusRequest;
import com.educacional.schoolacademicperiods.domain.enumeration.SortType;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.AcademicYearQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.impl.PageDomainImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.educacional.schoolacademicperiods.domain.enumeration.Status.ACTIVE;

public class SchoolAcademicPeriodYearTemplate {
    public static final String FIELD_SCHOOL_ID = "schoolId";
    public static final String FIELD_YEAR = "year";

    public static final String ACADEMIC_YEAR_ID = "79c9438f-d486-4fec-8388-f78c5e1fe0f4";
    public static final String SCHOOL_ID = "770f654f-1a03-4cdb-9a14-5ede5cb0c718";
    public static final String TITLE = "Year 2021";
    public static final String YEAR = "2021";
    public static final Instant CREATE_AT = Instant.parse("2021-09-06T17:15:21.514963488Z");
    public static final Instant UPDATE_AT = Instant.parse("2021-09-06T17:15:21.514963488Z");
    ;

    public static final String FIELD_NAME = "name";
    public static final int PAG = 1;
    public static final int LIMIT = 1;
    public static final String FIELD_SORT_ASC = "ASC";
    public static final String FIELD_SORT_DESC = "DESC";
    public static final String CREATED_AT_START = "2021-11-30T11:01:44.203Z";
    public static final String CREATED_AT_END = "2021-12-30T11:01:44.203Z";

    public static AcademicYear getAcademicYearTemplate() {
        return new AcademicYear()
                .withId(ACADEMIC_YEAR_ID)
                .withTitle(TITLE)
                .withSchoolId(SCHOOL_ID)
                .withYear(YEAR)
                .withStatus(ACTIVE)
                .withCreatedAt(CREATE_AT)
                .withUpdatedAt(UPDATE_AT);
    }

    public static AcademicYearRequest getAcademicYearRequestTemplate() {
        return new AcademicYearRequest().withTitle(TITLE).withYear(YEAR);
    }

    public static UpdateYearStatusRequest getYearUpdateStatusRequest() {
        return new UpdateYearStatusRequest().withStatus(ACTIVE.toString());
    }

    public static List<AcademicYear> getListofAcademicYearTemplate() {
        return List.of(getAcademicYearTemplate(), getAcademicYearTemplate(), getAcademicYearTemplate());
    }

    public static AcademicYearQueryFilter getAcademicYearQueryFilter() {
        var academicYearQueryFilter = new AcademicYearQueryFilter();
        academicYearQueryFilter.setLimit(LIMIT);
        academicYearQueryFilter.setId(ACADEMIC_YEAR_ID);
        academicYearQueryFilter.setYear(YEAR);
        academicYearQueryFilter.setStatus("ACTIVE");
        academicYearQueryFilter.setPage(PAG);
        academicYearQueryFilter.setSort(FIELD_NAME);
        academicYearQueryFilter.setSchoolId(SCHOOL_ID);
        academicYearQueryFilter.setSortType(SortType.valueOf(FIELD_SORT_ASC));
        academicYearQueryFilter.setCreatedAtStart(Instant.parse(CREATED_AT_START));
        academicYearQueryFilter.setCreatedAtEnd(Instant.parse(CREATED_AT_END));
        return academicYearQueryFilter
                .withYear(YEAR)
                .withId(ACADEMIC_YEAR_ID)
                .withStatus("ACTIVE")
                .withSchoolId(SCHOOL_ID)
                .withCreatedAtStart(Instant.parse(CREATED_AT_START))
                .withCreatedAtEnd(Instant.parse(CREATED_AT_END));
    }

    public static PageDomain<AcademicYear> getListofPageDomainAcademicYearTemplate() {
        var academicYearFiltered = getListofAcademicYearTemplate();
        return new PageDomainImpl<>(academicYearFiltered, 10L, 1L);
    }

    public static PageDomain<AcademicYear> getEmptyAcademicYearPageDomain() {
        List<AcademicYear> lista = new ArrayList<>();
        return new PageDomainImpl<AcademicYear>(lista, 0, 1);
    }

    public static UpdateAcademicYearRequest getAcademicYearUpdateTemplate() {
        UpdateAcademicYearRequest updateAcademicYearRequest = new UpdateAcademicYearRequest();
        updateAcademicYearRequest.setYear(YEAR);
        updateAcademicYearRequest.setTitle(TITLE);
        return updateAcademicYearRequest
                .withTitle(TITLE)
                .withYear(YEAR);
    }
}
