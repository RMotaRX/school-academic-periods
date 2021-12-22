package com.educacional.schoolacademicperiods.application.rest.spec;

import com.educacional.schoolacademicperiods.application.rest.request.AcademicPeriodQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.CreateSchoolAcademicPeriodsRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateSchoolAcademicPeriodRequest;
import com.educacional.schoolacademicperiods.application.rest.response.AcademicYearResponse;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicStatusRequest;
import com.educacional.schoolacademicperiods.application.rest.response.SchoolAcademicPeriodsResponse;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseBadGateway;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseBadRequest;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseForbidden;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseInternalServerError;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseNotFound;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseUnprocessableEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Academic period", description = "Management Academic period")
public interface SchoolAcademicPeriodsControllerSpec {
    @Operation(summary = "Create a academic period",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Action created successfully",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SchoolAcademicPeriodsResponse.class)
                            )
                    )
            })
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    SchoolAcademicPeriodsResponse create(@RequestBody CreateSchoolAcademicPeriodsRequest createSchoolAcademicPeriodsRequest,
                                         @PathVariable String schoolId);


    @Operation(
            summary = "Get academic period",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Object return successfully",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = SchoolAcademicPeriodsResponse.class)
                                    )
                            }
                    )
            }
    )
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    SchoolAcademicPeriodsResponse getDetails(String schoolId, String periodId);

    @Operation(
            summary = "Update single SchoolAcademicPeriod",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description ="Operation Completed"
                    )
            }
    )
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    void updateSchoolAcademicPeriod(@RequestBody(description = "Instructions to update SchoolAcademicsPeriod")
                                    final UpdateSchoolAcademicPeriodRequest updateSchoolAcademicPeriod,
                                    @Parameter(name = "id",required = true) @PathVariable final String schoolId,
                                    @Parameter(name = "id",required = true) @PathVariable final String periodId);


    @Operation(
            summary = "Get update status period",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Object return successfully",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
                    )
            }
    )
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    void updateStatus(@Parameter(name = "id", required = true) String schoolId, @Parameter(name = "periodId", required = true) String periodId, @RequestBody UpdateAcademicStatusRequest updateAcademicStatusRequest);

    @Operation(
            summary = "Get a list of Academic Periods",
            parameters = {
                    @Parameter(
                            description = "Identifier of a school type organization",
                            name = "schoolId",
                            required = true,
                            schema = @Schema(type = "String($uuid)", example = "838489f9-c288-4ec8-9008-e2a8a62b2869"))},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get a list of academic year",
                            headers = {
                                    @Header(
                                            name = "Accept-Ranges",
                                            description = "Max number of objects returned at once",
                                            schema = @Schema(type = "integer", example = "30")),
                                    @Header(
                                            name = "Content-Range",
                                            description = "Total disciplines found",
                                            schema = @Schema(type = "string", example = "12850"))
                            },
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = SchoolAcademicPeriodsResponse.class)))),
            })
    @ApiResponseForbidden
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    ResponseEntity<List<SchoolAcademicPeriodsResponse>> listSchoolAcademicPeriodsResponse(@PathVariable final String schoolId,
                                                                                          @ParameterObject AcademicPeriodQueryFilterRequest academicPeriodQueryFilterRequest);

    @Operation(
            summary = "Delete academic period",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Object successfully removed",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AcademicYearResponse.class)))
            })
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseNotFound
    @ApiResponseInternalServerError
    @ApiResponseUnprocessableEntity
    @ApiResponseBadGateway
    void deleteAcademicPeriod(@Parameter(name = "schoolId", required = true, description = "Identifier of a school type organization", schema = @Schema(type = "String", format = "UUID", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")) @PathVariable String schoolId, @Parameter(name = "periodId", required = true, description = "Identifier of the academic period", schema = @Schema(type = "String", format = "UUID", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")) @PathVariable String periodId);
}
