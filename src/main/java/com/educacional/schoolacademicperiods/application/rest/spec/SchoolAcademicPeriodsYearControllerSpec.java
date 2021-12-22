package com.educacional.schoolacademicperiods.application.rest.spec;

import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateYearStatusRequest;
import com.educacional.schoolacademicperiods.application.rest.response.AcademicYearResponse;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseBadGateway;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseBadRequest;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseForbidden;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseInternalServerError;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseNotFound;
import com.educacional.schoolacademicperiods.application.rest.spec.common.response.ApiResponseUnprocessableEntity;
import io.swagger.v3.oas.annotations.Operation;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Academic year", description = "Academic year management")
public interface SchoolAcademicPeriodsYearControllerSpec {
    @Operation(
            summary = "Create an academic year",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AcademicYearResponse.class))),
            })
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    AcademicYearResponse createAcademicYear(String id, AcademicYearRequest academicYearRequest);

    @Operation(
            summary = "Update year academic status",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "updated status"
                    )
            }
    )
    @ApiResponseBadRequest
    @ApiResponseForbidden
    @ApiResponseUnprocessableEntity
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    void updateStatus(@PathVariable String schoolId, String yearId, UpdateYearStatusRequest updateYearStatusRequest);


    @Operation(
            summary = "Get a list of Disciplines",
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
                                    array = @ArraySchema(schema = @Schema(implementation = AcademicYearResponse.class)))),
            })
    @ApiResponseForbidden
    @ApiResponseInternalServerError
    @ApiResponseBadGateway
    ResponseEntity<List<AcademicYearResponse>> findAllAcademicYearResponseBySchoolId (
            @ParameterObject AcademicYearQueryFilterRequest disciplineQueryFilterRequest, @PathVariable final String schoolId );

  @Operation(
      summary = "Update single Academic year",
      parameters = {
        @Parameter(
            description = "Identifier of the academic year",
            name = "yearId",
            required = true,
            schema =
                @Schema(type = "String($uuid)", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")),
        @Parameter(
            description = "Identifier of a school type organization",
            name = "schoolId",
            required = true,
            schema =
                @Schema(type = "String($uuid)", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")),
      },
      responses = {
        @ApiResponse(responseCode = "204", description = "Operation Completed"),
      })
  @ApiResponseNotFound
  @ApiResponseForbidden
  @ApiResponseInternalServerError
  @ApiResponseBadGateway
  void updateAcademicYear(
      @PathVariable final String yearId,
      @PathVariable final String schoolId,
      @RequestBody @Valid UpdateAcademicYearRequest updateAcademicYearRequest);

    @Operation(
            summary = "Delete academic year",
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
    void deleteAcademicYear(@Parameter(name = "schoolId", required = true, description = "Identifier of a school type organization", schema = @Schema(type = "String", format = "UUID", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")) @PathVariable String schoolId, @Parameter(name = "yearId", required = true, description = "Identifier of the academic year", schema = @Schema(type = "String", format = "UUID", example = "838489f9-c288-4ec8-9008-e2a8a62b2869")) @PathVariable String yearId);
}


