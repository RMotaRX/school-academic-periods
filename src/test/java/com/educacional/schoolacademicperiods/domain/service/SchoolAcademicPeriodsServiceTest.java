package com.educacional.schoolacademicperiods.domain.service;

import com.educacional.schoolacademicperiods.application.rest.enumerations.Status;
import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.client.SchoolClassClient;
import com.educacional.schoolacademicperiods.domain.exception.AcademicPeriodLinkedSchoolClassException;
import com.educacional.schoolacademicperiods.domain.exception.DuplicateResourceException;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.InvalidDateException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.mapper.SchoolAcademicPeriodDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.Period;
import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import com.educacional.schoolacademicperiods.templates.SchoolClassTemplate;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.educacional.schoolacademicperiods.templates.OrganizationTemplate.SCHOOL_ID;
import static com.educacional.schoolacademicperiods.templates.OrganizationTemplate.getOrganizationsTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.STATUS;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getPageDomanin;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getScholAcademicPeriods;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolAcademicPeriodsServiceTest {
    @Mock
    private SchoolAcademicPeriodsRepository schoolAcademicPeriodsRepository;
    @Mock
    private SchoolAcademicPeriodsYearRepository schoolAcademicPeriodsYearRepository;

    @Mock
    private DeletedRecord deletedRecord;

    @Mock
    private OrganizationClient organizationClient;

    @Mock
    private SchoolClassClient schoolClassClient;

    @Mock
    private AcademicYearDomainMapper academicYearDomainMapper;

    @Mock
    private SchoolAcademicPeriodDomainMapper schoolAcademicMapper;

    @InjectMocks
    private SchoolAcademicPeriodsService schoolAcademicPeriodsService;

    @Mock
    private SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldCreateAcademicPeriodGivenValidParameters() {
        var academicPeriod = getScholAcademicPeriods();
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any())).thenReturn(getAcademicYearTemplate());
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        schoolAcademicPeriodsService.createAcademicPeriod(academicPeriod, ID);

        verify(schoolAcademicPeriodsRepository, times(1)).save(academicPeriod);
        assertNotNull(academicPeriod.getId(), "Id shouldn't be null");
        assertEquals(Status.ACTIVE.toString(), academicPeriod.getStatus().toString());
    }

    @Test
    void shouldThrowDuplicateKeyExceptionWhenCreateAcademicPeriods() {
        var academicPeriod = getScholAcademicPeriods();

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any())).thenReturn(getAcademicYearTemplate());
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));
        when(schoolAcademicPeriodsRepository.save(any())).thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateResourceException.class, () -> schoolAcademicPeriodsService.createAcademicPeriod(academicPeriod, ID));


    }


    @Test
    void shouldThrowOrganizationNotFoundExceptionWhenCreateAcademicPeriods() {
        var academicPeriod = getScholAcademicPeriods();
        when(organizationClient.getOrganization(any())).thenReturn(new ArrayList<>());
        when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any())).thenReturn(getAcademicYearTemplate());
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        assertThrows(OrganizationNotFoundException.class, () -> schoolAcademicPeriodsService.createAcademicPeriod(academicPeriod, ID));
    }

    @Test
    void shouldThrowOrganizationServiceUnavailableExceptionWhenCreateAcademicPeriod() {
        var academicPeriod = getScholAcademicPeriods();
        when(organizationClient.getOrganization(any())).thenThrow(RetryableException.class);
        when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any())).thenReturn(getAcademicYearTemplate());
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        assertThrows(OrganizationServiceUnavailableException.class, () -> schoolAcademicPeriodsService.createAcademicPeriod(academicPeriod, ID));
    }

    @Test
    void shouldFindAcademicPeriod() {
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getScholAcademicPeriods()));

        when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any())).thenReturn(getAcademicYearTemplate());
        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        schoolAcademicPeriodsService.findAcademicPeriod(SCHOOL_ID, ID);

        verify(schoolAcademicPeriodsRepository, times(1)).findAcademicPeriodBySchoolIdAndId(any(), any());

    }

    @Test
    void shouldListSchoolAcademicPeriod() {
        when(schoolAcademicPeriodsRepository.findAllAcademicResponseBySchoolId(any(), any()))
                .thenReturn(getPageDomanin());

        var listAcademic = schoolAcademicPeriodsService.findAllAcademicResponseBySchoolId(any(), any());
        assertNotNull(listAcademic);
        assertFalse(listAcademic.getContent().isEmpty());
        verify(schoolAcademicPeriodsRepository, times(1)).findAllAcademicResponseBySchoolId(any(), any());
    }

    @Test
    void shouldFindAcademicPeriodGivenValidId() {
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getScholAcademicPeriods()));

        schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any());

    }


    @Test
    void shouldUpdateSchoolPeriodGivenValidParameters() throws Exception {
        var period = getScholAcademicPeriods();

        when(schoolAcademicPeriodsRepository.save(period)).thenReturn(period);

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getScholAcademicPeriods()));


        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        schoolAcademicPeriodsService.update(period);

        verify(schoolAcademicPeriodsRepository, times(1)).save(any());
        verify(schoolAcademicPeriodsRepository, times(1)).findAcademicPeriodBySchoolIdAndId(any(), any());
    }

    @Test
    void shouldThrowDuplicateResourceExceptionWhenUpdateAcademicPeriod() throws Exception {
        var period = getScholAcademicPeriods();

        when(schoolAcademicPeriodsRepository.save(period)).thenReturn(period);

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getScholAcademicPeriods()));

        when(schoolAcademicPeriodsRepository.save(any()))
                .thenThrow(DuplicateKeyException.class);

        when(schoolAcademicPeriodsYearRepository.findBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getAcademicYearTemplate()));

        assertThrows(DuplicateResourceException.class, () -> schoolAcademicPeriodsService.update(period));

    }

    @Test
    void shouldThrowInvalidDateExceptionGivenInvalidDate() {
        var period = getScholAcademicPeriods().withPeriod(new Period(LocalDate.parse("2021-05-05"), LocalDate.parse("2021-01-01")));

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getScholAcademicPeriods()));

        assertThrows(InvalidDateException.class, () -> schoolAcademicPeriodsService.update(period));
    }

    @Test
    void shouldThrowOrganizationNotFoundGivenInvalidOrganization() {

        doThrow(new OrganizationNotFoundException(ExceptionCode.ORGANIZATION_NOT_FOUND)).when(organizationClient).getOrganization(any());

        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any()))
                .thenReturn(Optional.of(getScholAcademicPeriods()));

        assertThrows(OrganizationNotFoundException.class, () -> organizationClient.getOrganization(any()));
    }

    @Test
    void shouldThrowDuplicateResourceException() {
        doThrow(new DuplicateResourceException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION)).when(schoolAcademicPeriodsRepository).save(any());
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        assertThrows(DuplicateResourceException.class, () -> schoolAcademicPeriodsService.createAcademicPeriod(getScholAcademicPeriods(), ID));
    }

    @Test
    void shouldThrowOrganizationServiceUnavailableException() {
        doThrow(new OrganizationServiceUnavailableException(ExceptionCode.DUPLICATE_RESOURCE_EXCEPTION))
                .when(organizationClient).getOrganization(any());
        assertThrows(OrganizationServiceUnavailableException.class, () -> schoolAcademicPeriodsService.createAcademicPeriod(getScholAcademicPeriods(), ID));
    }

    @Test
    void shouldUpdateStatusAcademic() {
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(getScholAcademicPeriods()));
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());

        schoolAcademicPeriodsService.updateStatus(SCHOOL_ID, ID, STATUS);

        verify(schoolAcademicPeriodsRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteAcademicPeriod() throws Exception {
        var academicPeriod = getScholAcademicPeriods();

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolClassClient.getSchoolClass(any(), any())).thenReturn(new ArrayList<SchoolClass>());
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicPeriod));

        schoolAcademicPeriodsService.deleteAcademicPeriod(ID, SCHOOL_ID);

        verify(deletedRecord, times(1)).saveDeletedRecord(any(), any(), any());
        verify(schoolAcademicPeriodsRepository, times(1)).delete(academicPeriod);
    }

    @Test
    void shouldThrowAcademicPeriodLinkedSchoolClassExceptionWhenDelete() throws Exception {
        var academicPeriod = getScholAcademicPeriods();

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        var schoolClass = SchoolClassTemplate.getSchoolClassQueryParams();

        when(schoolClassClient.getSchoolClass(any(), any())).thenReturn(List.of(SchoolClassTemplate.getSchoolClassTemplate()));
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicPeriod));

        assertThrows(AcademicPeriodLinkedSchoolClassException.class, () -> schoolAcademicPeriodsService.deleteAcademicPeriod(SCHOOL_ID, ID));
    }

    @Test
    void shouldThrowSchoolClassServiceUnavailableExceptionWhenDelete() throws Exception {
        var academicPeriod = getScholAcademicPeriods();

        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        when(schoolClassClient.getSchoolClass(any(), any())).thenThrow(RetryableException.class);
        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicPeriod));

        assertThrows(SchoolClassServiceUnavailableException.class, () -> schoolAcademicPeriodsService.deleteAcademicPeriod(SCHOOL_ID, ID));
    }


    @Test
    void shouldRollBackDeletedSchoolAcademicPeriodWhenSomeErrorsOccurs() throws Exception {
        var academicPeriod = getScholAcademicPeriods();

        when(schoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(academicPeriod));
        when(organizationClient.getOrganization(any())).thenReturn(getOrganizationsTemplate());
        doThrow(new RuntimeException("Error Test")).when(schoolAcademicPeriodsRepository).delete(academicPeriod);

        assertThrows(InternalServiceException.class, () -> schoolAcademicPeriodsService.deleteAcademicPeriod(ID, SCHOOL_ID));

        verify(deletedRecord, times(1)).deleteDeletedRecord(any());
    }

}

