package com.educacional.schoolacademicperiods.domain.service;

import com.educacional.schoolacademicperiods.application.rest.request.UpdateYearStatusRequest;
import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.enumeration.DatabaseIndexes;
import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
import com.educacional.schoolacademicperiods.domain.enumeration.Status;
import com.educacional.schoolacademicperiods.domain.exception.DuplicateResourceException;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.helper.ValidatorHelper;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.AcademicYearQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.Organization;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.educacional.schoolacademicperiods.domain.enumeration.Status.ACTIVE;

@RequiredArgsConstructor
@Slf4j
public class SchoolAcademicPeriodsYearService {

    private final SchoolAcademicPeriodsYearRepository schoolAcademicPeriodsYearRepository;
    private final OrganizationClient organizationClient;
    private final AcademicYearDomainMapper academicYearDomainMapper;
    private final DeletedRecord deletedRecord;

    public AcademicYear findAcademicYear(String schoolId, String yearId) {
        return schoolAcademicPeriodsYearRepository
                .findBySchoolIdAndId(schoolId, yearId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.ACADEMIC_YEAR_NOT_FOUND));
    }

    public AcademicYear createAcademicYear(AcademicYear academicYear) {

        checkSchool(academicYear.getSchoolId());

        try {
            academicYear.setId(UUID.randomUUID().toString());
            academicYear.setStatus(ACTIVE);

            ValidatorHelper.validate(academicYear);

            log.info(
                    "Creating academic year [id = {}, year = {}, title = {}]",
                    academicYear.getId(),
                    academicYear.getYear(),
                    academicYear.getTitle());

            return schoolAcademicPeriodsYearRepository.save(academicYear);
        } catch (DuplicateKeyException e) {
            if (e.toString().contains(DatabaseIndexes.SCHOOL_YEAR.getValue())) {
                throw new DuplicateResourceException(
                        ExceptionCode.DUPLICATE_YEAR_EXCEPTION,
                        academicYear.getYear(),
                        academicYear.getSchoolId());
            }
            throw new DuplicateResourceException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION);
        }
    }

    public void updateYearStatus(String schoolid, String yearId, UpdateYearStatusRequest updateYearStatusRequest) {
        var year = this.findAcademicYear(schoolid, yearId);
        log.info("Update year status with [id {}]", year.getId());
        schoolAcademicPeriodsYearRepository.save(year.withStatus(Status.valueOf(updateYearStatusRequest.getStatus())));
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

    public PageDomain<AcademicYear> findAllAcademicYearResponseBySchoolId(
            AcademicYearQueryFilter academicYearQueryFilter, String schoolId) {
        return schoolAcademicPeriodsYearRepository.findAllAcademicYearResponseBySchoolId(
                academicYearQueryFilter, schoolId);
    }

    public void updateAcademicYear(String yearId, String schoolId, AcademicYear updateAcademicYear) {

        var savedAcademicYear = findAcademicYear(schoolId, yearId);
        try {
            academicYearDomainMapper.merge(updateAcademicYear, savedAcademicYear);
            checkSchool(savedAcademicYear.getSchoolId());
            log.info(
                    "Updated Academic Year [id = {}] of [schoolId={}]",
                    savedAcademicYear.getId(),
                    savedAcademicYear.getSchoolId());
            ValidatorHelper.validate(savedAcademicYear);
            schoolAcademicPeriodsYearRepository.save(savedAcademicYear);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION);
        }
    }

    public void deleteAcademicYear(String yearId, String schoolId) {
        var academicYear = findAcademicYear(schoolId, yearId);

        deletedRecord.saveDeletedRecord(yearId, academicYear, Instant.now());

        try {
            schoolAcademicPeriodsYearRepository.delete(academicYear);
        } catch (Exception e) {
            log.error("An internal server error has occurred during School Academic Period Year deletion");
            log.error(e.getMessage(), e);
            deletedRecord.deleteDeletedRecord(yearId);
            throw new InternalServiceException(ExceptionCode.INTERNAL_SERVER_ERROR);

        }

    }
}
