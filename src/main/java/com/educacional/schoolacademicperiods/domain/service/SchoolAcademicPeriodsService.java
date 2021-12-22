package com.educacional.schoolacademicperiods.domain.service;

import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.client.SchoolClassClient;
import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
import com.educacional.schoolacademicperiods.domain.enumeration.PeriodType;
import com.educacional.schoolacademicperiods.domain.exception.AcademicPeriodLinkedSchoolClassException;
import com.educacional.schoolacademicperiods.domain.enumeration.Status;
import com.educacional.schoolacademicperiods.domain.exception.BusinessException;
import com.educacional.schoolacademicperiods.domain.exception.DuplicateResourceException;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.InvalidDateException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.helper.ValidatorHelper;
import com.educacional.schoolacademicperiods.domain.mapper.SchoolAcademicPeriodDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.AcademicPeriodQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.Organization;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.dao.DuplicateKeyException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.educacional.schoolacademicperiods.domain.enumeration.Status.ACTIVE;

@RequiredArgsConstructor
@Slf4j
public class SchoolAcademicPeriodsService {
    private final SchoolAcademicPeriodsRepository schoolAcademicPeriodsRepository;
    private final OrganizationClient organizationClient;
    private final SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;
    private final DeletedRecord deletedRecord;
    private final SchoolClassClient schoolClassClient;
    private final SchoolAcademicPeriodDomainMapper schoolAcademicPeriodDomainMapper;

    public SchoolAcademicPeriods findAcademicPeriod(String schoolId, String periodId) {
        this.checkSchool(schoolId);
        return schoolAcademicPeriodsRepository
                .findAcademicPeriodBySchoolIdAndId(schoolId, periodId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.ACADEMIC_PERIOD_NOT_FOUND));
    }

    public SchoolAcademicPeriods createAcademicPeriod(
            SchoolAcademicPeriods schoolAcademicPeriods, String schoolId) {
        try {
            schoolAcademicPeriods.setId(UUID.randomUUID().toString());
            schoolAcademicPeriods.setStatus(ACTIVE);
            schoolAcademicPeriods.setSchoolId(schoolId);
            schoolAcademicPeriods.setType(PeriodType.SCHOOL_YEAR);

            this.checkValidations(schoolAcademicPeriods);
            log.info("Create schoolAcademicPeriod with id{}", schoolAcademicPeriods.getId());
            return schoolAcademicPeriodsRepository.save(schoolAcademicPeriods);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION);
        }
    }

    private void checkSchoolClass(String periodId, String schoolId) {
        try {
            var list = schoolClassClient.getSchoolClass(schoolId, new SchoolClass().withPeriodId(periodId));
            if (!list.isEmpty()) {
                throw new AcademicPeriodLinkedSchoolClassException(ExceptionCode.ACADEMIC_PERIOD_LINKED_WITH_SCHOOL_CLASS);
            }
        } catch (RetryableException e) {
            throw new SchoolClassServiceUnavailableException(ExceptionCode.SCHOOL_CLASS_SERVICE_UNAVAILABLE_EXCEPTION);
        }
    }

    private void checkValidations(SchoolAcademicPeriods schoolAcademicPeriods) {
        this.checkSchool(schoolAcademicPeriods.getSchoolId());
        checkDate(
                schoolAcademicPeriods.getPeriod().getStartDate(),
                schoolAcademicPeriods.getPeriod().getEndDate());
        schoolAcademicPeriodsYearService.findAcademicYear(schoolAcademicPeriods.getSchoolId(), schoolAcademicPeriods.getYearId());
    }

    private void checkDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidDateException(ExceptionCode.INVALID_DATE);
        }
    }


    private void checkSchool(String schoolId) {
        try {

            List<Organization> organization =
                    organizationClient.getOrganization(new Organization(schoolId, OrganizationType.SCHOOL));
            if (organization.isEmpty()) {
                throw new OrganizationNotFoundException(
                        ExceptionCode.ORGANIZATION_NOT_FOUND, OrganizationType.SCHOOL.getValue(), schoolId);
            }
        } catch (RetryableException e) {
            log.error("Error on call organizations-ms.");
            throw new OrganizationServiceUnavailableException(
                    ExceptionCode.ORGANIZATION_SERVICE_UNAVAILABLE_EXCEPTION);
        }
    }

    public PageDomain<SchoolAcademicPeriods> findAllAcademicResponseBySchoolId(AcademicPeriodQueryFilter academicYearQueryFilter, String schoolId) {
        return schoolAcademicPeriodsRepository.findAllAcademicResponseBySchoolId(schoolId, academicYearQueryFilter);
    }

    public void deleteAcademicPeriod(String schoolId, String periodId) {
        var academicPeriod = findAcademicPeriod(schoolId, periodId);


        this.checkSchoolClass(periodId, schoolId);
        deletedRecord.saveDeletedRecord(periodId, academicPeriod, Instant.now());


        try {
            schoolAcademicPeriodsRepository.delete(academicPeriod);
        } catch (Exception e) {
            log.error("An internal server error has occurred during School Academic Period deletion");
            log.error(e.getMessage());
            deletedRecord.deleteDeletedRecord(periodId);
            throw new InternalServiceException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void update(SchoolAcademicPeriods updateSchoolAcademicPeriods) {
        try {
            var savedPeriod = this.findAcademicPeriod(updateSchoolAcademicPeriods.getSchoolId(), updateSchoolAcademicPeriods.getId());
            schoolAcademicPeriodDomainMapper.merge(updateSchoolAcademicPeriods, savedPeriod);
            checkValidations(updateSchoolAcademicPeriods);
            log.info("Updated schoolAcademicPeriod [id = {}, schoolId = {}", savedPeriod.getId(), savedPeriod.getSchoolId());
            schoolAcademicPeriodsRepository.save(savedPeriod);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION);
        }

    }

    public void updateStatus(String schoolId, String periodId, String updateStatus) {
        var academicSave = this.findAcademicPeriod(schoolId, periodId);
        academicSave.setStatus(Status.valueOf(updateStatus));
        ValidatorHelper.validate(academicSave);
        schoolAcademicPeriodsRepository.save(academicSave);
    }

}
