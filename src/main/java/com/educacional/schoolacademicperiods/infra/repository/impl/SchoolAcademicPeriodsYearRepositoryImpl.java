package com.educacional.schoolacademicperiods.infra.repository.impl;

import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.AcademicYearQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.impl.PageDomainImpl;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.infra.repository.MongoDbSchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.infra.repository.MongoDbSchoolAcademicYearRepository;
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
public class SchoolAcademicPeriodsYearRepositoryImpl implements SchoolAcademicPeriodsYearRepository {

  private final MongoDbSchoolAcademicYearRepository mongoDbSchoolAcademicYearRepository;
  private final MongoTemplate mongoTemplate;
  private final MongoDbSchoolAcademicPeriodsRepository mongoDbSchoolAcademicPeriodsRepository;

    @Override
    public AcademicYear save(AcademicYear academicYear) {
        return mongoDbSchoolAcademicYearRepository.save(academicYear);
    }

  @Override
  public Optional<AcademicYear> findBySchoolIdAndId(String schoolId, String yearId) {
    return mongoDbSchoolAcademicYearRepository.findAcademicYearBySchoolIdAndId(schoolId, yearId);
  }

  @Override
  public PageDomain<AcademicYear> findAllAcademicYearResponseBySchoolId(AcademicYearQueryFilter academicYearQueryFilter, String schoolId) {
    int page = academicYearQueryFilter.getPage() > 0 ? academicYearQueryFilter.getPage() - 1 : 0;

    Pageable pageable = PageRequest.of(page, academicYearQueryFilter.getLimit());

    var query = new Query();

    addCriteria(academicYearQueryFilter, schoolId, query);
    addSort(academicYearQueryFilter, query);

    var count = mongoTemplate.count(query, AcademicYear.class);
    long totalPages = (long) Math.ceil((double) count / academicYearQueryFilter.getLimit());

    var academicYearFiltered = mongoTemplate.find(query.with(pageable), AcademicYear.class);

    return new PageDomainImpl<>(academicYearFiltered, count, totalPages);
  }

  @Override
  public void delete(AcademicYear academicYear) {
    mongoDbSchoolAcademicYearRepository.delete(academicYear);
  }

  private void addSort(AcademicYearQueryFilter academicYearQueryFilter, Query query) {
    Optional.ofNullable(academicYearQueryFilter.getSort())
            .ifPresent(
                    sortField -> query.with(Sort.by(
                            Sort.Direction.fromString(String.valueOf(academicYearQueryFilter.getSortType())), String.valueOf(sortField)))
            );
  }

  private void addCriteria(AcademicYearQueryFilter academicYearQueryFilter, String schoolId, Query query) {
    if (academicYearQueryFilter.getCreatedAtStart() != null && academicYearQueryFilter.getCreatedAtEnd() != null) {
      query.addCriteria(new Criteria().andOperator(
              Criteria.where("createdAt").gte(academicYearQueryFilter.getCreatedAtStart()),
              Criteria.where("createdAt").lte(academicYearQueryFilter.getCreatedAtEnd())));

    } else if (academicYearQueryFilter.getCreatedAtStart() != null) {
      query.addCriteria(Criteria.where("createdAt").gte(academicYearQueryFilter.getCreatedAtStart()));
    } else if (academicYearQueryFilter.getCreatedAtEnd() != null) {
      query.addCriteria(Criteria.where("createdAt").lte(academicYearQueryFilter.getCreatedAtEnd()));
    }

    Optional.ofNullable(academicYearQueryFilter.getId())
            .ifPresent(academicYearId -> query.addCriteria(Criteria.where("id").is(academicYearId)));

    Optional.ofNullable(academicYearQueryFilter.getYear())
            .ifPresent(academicYearYear -> query.addCriteria(Criteria.where("year").is(academicYearYear)));

    Optional.ofNullable(academicYearQueryFilter.getStatus())
            .ifPresent(academicYearStatus -> query.addCriteria(Criteria.where("status").is(academicYearStatus)));

    query.addCriteria(Criteria.where("schoolId").is(schoolId));
  }
}
