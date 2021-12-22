package com.educacional.schoolacademicperiods.templates;

import com.educacional.schoolacademicperiods.application.rest.request.CreateSchoolAcademicPeriodsRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicStatusRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateSchoolAcademicPeriodRequest;
import com.educacional.schoolacademicperiods.domain.enumeration.Status;
import com.educacional.schoolacademicperiods.domain.model.AcademicPeriodQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.Period;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.domain.model.impl.PageDomainImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchoolAcademicPeriodTemplate {
    public static final String ID = "770f654f-1a03-4cdb-9a14-5ede5cb0c718";
    public static final String PERIOD_ID = "770f654f-1a03-4cdb-9a14-5ede5cb0c723";
    public static final String SCHOOL_ID = "770f652f-1a03-4cdb-9a14-5ede5cb0c723";

    public static final String FIELD_YEARID = "";


    public static final String FIELD_NAME = "name";
    public static final int PAG = 1;
    public static final int LIMIT = 1;
    public static final String FIELD_SORT_ASC = "ASC";
    public static final String FIELD_SORT_DESC = "DESC";
    public static final String CREATED_AT_START = "2021-11-30T11:01:44.203Z";
    public static final String CREATED_AT_END = "2021-12-30T11:01:44.203Z";
    public static final String STATUS = "ACTIVE";

    public static SchoolAcademicPeriods getScholAcademicPeriods() {
        return new SchoolAcademicPeriods()
                .withSchoolId(ID)
                .withStatus(Status.ACTIVE)
                .withPeriod(getPeriodTemplate())
                .withYearId(ID);
    }

    public static CreateSchoolAcademicPeriodsRequest getAcademicPeriodRequest() {
        return new CreateSchoolAcademicPeriodsRequest()
                .withYearId(ID)
                .withEndDate("2020-10-04")
                .withStartDate("2020-02-02")
                .withTitle("title");
    }

    public static UpdateSchoolAcademicPeriodRequest getUpdateSchoolAcademicPeriod() {
        return new UpdateSchoolAcademicPeriodRequest()
                .withYearId(ID)
                .withTitle("Title")
                .withEndDate("2020-10-04")
                .withStartDate("2020-02-02");
    }

    public static Period getPeriodTemplate() {
        return new Period(LocalDate.now(), LocalDate.now());
    }

    public static PageDomain<SchoolAcademicPeriods> getPageDomanin() {
        return new PageDomainImpl<SchoolAcademicPeriods>(List.of(getScholAcademicPeriods()), 1, 1);
    }

    public static AcademicPeriodQueryFilter getAcademicPeriodQueryFilter() {
        AcademicPeriodQueryFilter academicPeriodQueryFilter = new AcademicPeriodQueryFilter();
        academicPeriodQueryFilter.setPeriodId(PERIOD_ID);
        academicPeriodQueryFilter.setPage(PAG);
        academicPeriodQueryFilter.setLimit(LIMIT);
        return academicPeriodQueryFilter;
    }

    public static UpdateAcademicStatusRequest getUpdateAcademicStatusRequest() {

        return new UpdateAcademicStatusRequest().withStatus("INACTIVE");
    }

    public static List<SchoolAcademicPeriods> getListSchoolAcademicPeriodTemplate() {
        var listAcademicPeriod = new ArrayList<SchoolAcademicPeriods>();
        listAcademicPeriod.add(getScholAcademicPeriods());
        return listAcademicPeriod;
    }



}
