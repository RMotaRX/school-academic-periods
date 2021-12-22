package com.educacional.schoolacademicperiods.application.rest.handler;

import com.educacional.schoolacademicperiods.application.i18n.Message;
import com.educacional.schoolacademicperiods.application.i18n.MessageProperties;
import com.educacional.schoolacademicperiods.application.rest.response.ErrorData;
import com.educacional.schoolacademicperiods.application.rest.response.ErrorResponse;
import com.educacional.schoolacademicperiods.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Message messageService;

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(MessageProperties.API_FIELDS_INVALID.toString())
                .withMessage(messageService.get(MessageProperties.API_FIELDS_INVALID))
                .withTimestamp(Instant.now());
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(MessageProperties.API_FIELDS_INVALID.toString())
                .withMessage(messageService.get(MessageProperties.API_FIELDS_INVALID))
                .withTimestamp(Instant.now());

        body.setDetails(populateErrors(body, ex.getBindingResult()));
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(MessageProperties.API_FIELDS_INVALID.toString())
                .withMessage(messageService.get(MessageProperties.API_FIELDS_INVALID))
                .withTimestamp(Instant.now());

        body.setDetails(populateErrors(body, ex.getBindingResult()));
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(MessageProperties.API_BODY_INVALID.toString())
                .withMessage(messageService.get(MessageProperties.API_BODY_INVALID))
                .withTimestamp(Instant.now());

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> internalServerErrorExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(MessageProperties.INTERNAL_SERVER_ERROR.toString())
                .withMessage(messageService.get(MessageProperties.INTERNAL_SERVER_ERROR))
                .withTimestamp(Instant.now());

        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withMessage(messageService.get(MessageProperties.API_FIELDS_INVALID))
                .withCode(MessageProperties.API_FIELDS_INVALID.toString())
                .withTimestamp(Instant.now());

        populateErrors(body, ex.getConstraintViolations());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Object> businessExceptionHandler(BusinessException ex, WebRequest request) {
        ErrorResponse body = new ErrorResponse()
                .withCode(ex.getCodeAsString())
                .withMessage(messageService.get(ex.getCodeAsString(), ex.getCustomMessageFields()))
                .withTimestamp(Instant.now());

        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    private List<ErrorData> populateErrors(ErrorResponse response, BindingResult bindingResult) {
        List<ErrorData> errorsList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ErrorData error = new ErrorData()
                    .withField(fieldError.getField())
                    .withValue(fieldError.getRejectedValue())
                    .withMessage(messageService.get(fieldError));

            errorsList.add(error);
        }
        response.setDetails(errorsList);

        return errorsList;
    }

    private List<ErrorData> populateErrors(ErrorResponse response, Set<ConstraintViolation<?>> errors) {
        List<ErrorData> errorsList = new ArrayList<>();

        for (ConstraintViolation<?> fieldError : errors) {
            String field = null;
            for (Node node : fieldError.getPropertyPath()) {
                field = node.getName();
            }
            String message = MessageFormat.format(fieldError.getMessage(), field);
            ErrorData error = new ErrorData()
                    .withField(field)
                    .withValue(fieldError.getInvalidValue())
                    .withMessage(message);
            errorsList.add(error);
        }

        response.setDetails(errorsList);

        return errorsList;
    }
}
