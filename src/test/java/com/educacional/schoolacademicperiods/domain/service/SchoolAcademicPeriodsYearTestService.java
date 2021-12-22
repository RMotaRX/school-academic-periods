package com.educacional.schoolacademicperiods.domain.service;

import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.enumeration.Status;
import com.educacional.schoolacademicperiods.domain.exception.DuplicateResourceException;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.educacional.schoolacademicperiods.templates.OrganizationTemplate.SCHOOL_ID;
import static com.educacional.schoolacademicperiods.templates.OrganizationTemplate.getOrganizationsTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.ACADEMIC_YEAR_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearQueryFilter;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getEmptyAcademicYearPageDomain;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getListofPageDomainAcademicYearTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getYearUpdateStatusRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolAcademicPeriodsYearTestService {

    @Mock
    private SchoolAcademicPeriodsYearRepository schoolAcademicPeriodsYearRepository;


    @Mock
    private OrganizationClient organizationClient;
    @Mock
    private AcademicYearDomainMapper academicYearDomainMapper;


    @Mock
    private DeletedRecord deletedRecord;

    @InjectMocks
    private SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAcademicYearGivenValidParameters() {
        var academicYear = getAcademicYearTemplate();
        when(schoolAcademicPeriodsYearRepository.save(academicYear)).thenReturn(academicYear);
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        schoolAcademicPeriodsYearService.createAcademicYear(academicYear);

        verify(schoolAcademicPeriodsYearRepository, times(1)).save(academicYear);
        assertNotNull(academicYear.getId(), "Id shouldn't be null");
        assertEquals(Status.ACTIVE, academicYear.getStatus());
    }

    @Test
    void shouldUpdateStatusGivenValidParameters() {
        var updateYear = getYearUpdateStatusRequest();

        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        schoolAcademicPeriodsYearService.updateYearStatus(ID, ID, updateYear);

        verify(schoolAcademicPeriodsYearRepository, times(1)).save((any(AcademicYear.class)));
        verify(schoolAcademicPeriodsYearRepository, times(1)).findBySchoolIdAndId(any(), any());
    }


    @Test
    void shouldThrowConstraintViolationExceptionApplicationGivenInvalidParameters() {
        var academicYear = getAcademicYearTemplate().withYear(null);
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        assertThrows(
                ConstraintViolationException.class,
                () -> schoolAcademicPeriodsYearService.createAcademicYear(academicYear));
    }


    @Test
    void shouldThrowOrganizationNotFoundExceptionApplicationGivenInvalidParameters() {
        var academicYear = getAcademicYearTemplate().withYear(null);

        assertThrows(
                OrganizationNotFoundException.class,
                () -> schoolAcademicPeriodsYearService.createAcademicYear(academicYear));
    }

    @Test
    void shouldThrowDuplicateResourceException() {
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolAcademicPeriodsYearRepository.save(any(AcademicYear.class))).thenThrow(new DuplicateKeyException(any()));

        assertThrows(
                DuplicateResourceException.class,
                () -> schoolAcademicPeriodsYearService.createAcademicYear(getAcademicYearTemplate()));
    }

    @Test
    void shouldFindAcademicYearGivenValidId() {
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getAcademicYearTemplate()));

        schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any());

        verify(schoolAcademicPeriodsYearRepository, times(1)).findBySchoolIdAndId(any(), any());
    }


    @Test
    void shouldFindAcademicYearsGivenValidParameters() {
        var academicYearQueryFilter = getAcademicYearQueryFilter();
        var academicYearPageDomain = getListofPageDomainAcademicYearTemplate();
        var schoolId = SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate().getSchoolId();

        when(schoolAcademicPeriodsYearRepository.findAllAcademicYearResponseBySchoolId(academicYearQueryFilter, schoolId)).thenReturn(academicYearPageDomain);

        schoolAcademicPeriodsYearService.findAllAcademicYearResponseBySchoolId(academicYearQueryFilter, schoolId);

        assertNotNull(academicYearPageDomain);
        assertTrue(academicYearPageDomain.getTotal() > 0);
    }

    @Test
    void shouldFindEmptyAcademicYearsList() {
        var academicYearQueryFilter = getAcademicYearQueryFilter();
        var academicYearPageDomain = getEmptyAcademicYearPageDomain();
        var schoolId = SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate().getSchoolId();

        when(schoolAcademicPeriodsYearRepository.findAllAcademicYearResponseBySchoolId(academicYearQueryFilter, schoolId)).thenReturn(academicYearPageDomain);

        schoolAcademicPeriodsYearService.findAllAcademicYearResponseBySchoolId(academicYearQueryFilter, schoolId);

        assertNotNull(academicYearPageDomain);
        assertEquals(0, academicYearPageDomain.getTotal());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionGivenInvalidIdWhenUpdateDiscipline() {
        var updateDiscipline = getAcademicYearTemplate();
        assertThrows(ResourceNotFoundException.class, () -> schoolAcademicPeriodsYearService.updateAcademicYear(ACADEMIC_YEAR_ID, SCHOOL_ID, updateDiscipline));
    }


    @Test
    void shouldThrowAcademicYearNotFoundExceptionGivenInvalidParameters() throws Exception {

        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> schoolAcademicPeriodsYearService.findAcademicYear(any(), any()));

        verify(schoolAcademicPeriodsYearRepository, times(1)).findBySchoolIdAndId(any(), any());
    }

    @Test
    void shouldDeleteAcademicYearPeriod() throws Exception {
        var academicYear = getAcademicYearTemplate();

        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicYear));

        schoolAcademicPeriodsYearService.deleteAcademicYear(any(), any());

        verify(deletedRecord, times(1)).saveDeletedRecord(any(), any(), any());
        verify(schoolAcademicPeriodsYearRepository, times(1)).delete(academicYear);
    }

    @Test
    void shouldRollBackDeletedSchoolAcademicPeriodYearWhenSomeErrorsOccurs() throws Exception {
        var academicYear = getAcademicYearTemplate();

        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicYear));
        doThrow(new RuntimeException("Error Test")).when(schoolAcademicPeriodsYearRepository).delete(academicYear);

        assertThrows(InternalServiceException.class, () -> schoolAcademicPeriodsYearService.deleteAcademicYear(any(), any()));

        verify(deletedRecord, times(1)).deleteDeletedRecord(any());
    }

}