package com.educacional.schoolacademicperiods.infra.repository.impl;

import com.educacional.schoolacademicperiods.domain.model.AcademicPeriodQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.domain.model.impl.PageDomainImpl;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.infra.repository.MongoDbSchoolAcademicPeriodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchoolAcademicPeriodsRepositoryImpl implements SchoolAcademicPeriodsRepository {

    private final MongoDbSchoolAcademicPeriodsRepository mongoDbSchoolAcademicPeriodsRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public SchoolAcademicPeriods save(SchoolAcademicPeriods schoolAcademicPeriods) {
        return mongoDbSchoolAcademicPeriodsRepository.save(schoolAcademicPeriods);
    }

    @Override
    public PageDomain<SchoolAcademicPeriods> findAllAcademicResponseBySchoolId(String schoolId, AcademicPeriodQueryFilter academicPeriodQueryFilter) {
        int page = academicPeriodQueryFilter.getPage() > 0 ? academicPeriodQueryFilter.getPage() - 1 : 0;

        Pageable pageable = PageRequest.of(page, academicPeriodQueryFilter.getLimit());

        var query = new Query();

        addCriteria(academicPeriodQueryFilter, schoolId, query);
        addSort(academicPeriodQueryFilter, query);

        var count = mongoTemplate.count(query, SchoolAcademicPeriods.class);
        long totalPages = (long) Math.ceil((double) count / academicPeriodQueryFilter.getLimit());

        var academicYearFiltered = mongoTemplate.find(query.with(pageable), SchoolAcademicPeriods.class);

        return new PageDomainImpl<>(academicYearFiltered, count, totalPages);
    }

    @Override
    public Optional<SchoolAcademicPeriods> findAcademicPeriodBySchoolIdAndId(String schoolId, String periodId) {
        return mongoDbSchoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(schoolId,periodId);
    }

    @Override
    public void delete(SchoolAcademicPeriods academicPeriod) {
        mongoDbSchoolAcademicPeriodsRepository.delete(academicPeriod);
    }

    private void addSort(AcademicPeriodQueryFilter academicPeriodQueryFilter, Query query) {
        Optional.ofNullable(academicPeriodQueryFilter.getSort())
                .ifPresent(
                        sortField -> query.with(Sort.by(
                                Sort.Direction.fromString(String.valueOf(academicPeriodQueryFilter.getSortType())), String.valueOf(sortField)))
                );
    }

    private void addCriteria(AcademicPeriodQueryFilter academicPeriodQueryFilter, String schoolId, Query query) {
        if (academicPeriodQueryFilter.getCreatedAtStart() != null && academicPeriodQueryFilter.getCreatedAtEnd() != null) {
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("createdAt").gte(academicPeriodQueryFilter.getCreatedAtStart()),
                    Criteria.where("createdAt").lte(academicPeriodQueryFilter.getCreatedAtEnd())));

        } else if (academicPeriodQueryFilter.getCreatedAtStart() != null) {
            query.addCriteria(Criteria.where("createdAt").gte(academicPeriodQueryFilter.getCreatedAtStart()));
        } else if (academicPeriodQueryFilter.getCreatedAtEnd() != null) {
            query.addCriteria(Criteria.where("createdAt").lte(academicPeriodQueryFilter.getCreatedAtEnd()));
        }

        Optional.ofNullable(academicPeriodQueryFilter.getPeriodId())
                .ifPresent(periodId -> query.addCriteria(Criteria.where("id").is(periodId)));

        Optional.ofNullable(academicPeriodQueryFilter.getYearId())
                .ifPresent(yearId -> query.addCriteria(Criteria.where("yearId").is(yearId)));

        Optional.ofNullable(academicPeriodQueryFilter.getStatus())
                .ifPresent(academicYearStatus -> query.addCriteria(Criteria.where("status").is(academicYearStatus)));

        query.addCriteria(Criteria.where("schoolId").is(schoolId));
    }
}
