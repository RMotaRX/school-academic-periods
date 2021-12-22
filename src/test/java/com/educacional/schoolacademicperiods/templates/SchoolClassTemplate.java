package com.educacional.schoolacademicperiods.templates;

import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.infra.client.parameters.SchoolClassQueryParams;

import java.util.List;

public class SchoolClassTemplate {

    public static final String ID = "770f654f-1a03-4cdb-9a14-3ede5cb0c718";
    public static final String PERIOD_ID = "770f654f-1a03-4cdb-9a14-3ede5cb0c418";
    public static final String PERIOD_ID_UPDATE = "770f654f-1a03-4cdb-9a14-3ede5cb0c419";

    public static SchoolClass getSchoolClassTemplate() {
        return new SchoolClass()
                .withPeriodId(PERIOD_ID)
                .withId(ID);

    }

    public static SchoolClassQueryParams getSchoolClassQueryParams() {
        return new SchoolClassQueryParams()
                .withPeriodId(PERIOD_ID);

    }

    public static SchoolClassQueryParams getSchoolClassQueryParamsPeriodBefore() {
        var schoolclass = new SchoolClassQueryParams();
        schoolclass.setPeriodId(PERIOD_ID_UPDATE);
        return schoolclass;
    }

    public static List<SchoolClass> getSchoolClassListTemplate() {
        return List.of(getSchoolClassTemplate());
    }
}
