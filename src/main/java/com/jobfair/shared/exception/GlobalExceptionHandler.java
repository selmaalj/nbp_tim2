package com.jobfair.shared.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.jobfair.shared.response.ApiResponse;
import com.jobfair.shared.response.ValidationErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        field -> field.getDefaultMessage() == null ? "Invalid value" : field.getDefaultMessage(),
                        (first, second) -> first
                ));
        ValidationErrorResponse payload = new ValidationErrorResponse("Validation failed", errors);
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        Map<String, String> errors = ex.getAllValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> Map.entry(
                                result.getMethodParameter().getParameterName(),
                                error.getDefaultMessage() == null ? "Invalid value" : error.getDefaultMessage()
                        )))
                .collect(Collectors.toMap(
                        entry -> entry.getKey() == null ? "parameter" : entry.getKey(),
                        Map.Entry::getValue,
                        (first, second) -> first,
                        LinkedHashMap::new
                ));

        ValidationErrorResponse payload = new ValidationErrorResponse("Validation failed", errors);
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage() == null ? "Invalid value" : violation.getMessage(),
                        (first, second) -> first,
                        LinkedHashMap::new
                ));

        ValidationErrorResponse payload = new ValidationErrorResponse("Validation failed", errors);
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleMissingRequestParameter(MissingServletRequestParameterException ex) {
        ValidationErrorResponse payload = new ValidationErrorResponse(
                "Validation failed",
                Map.of(ex.getParameterName(), "Required request parameter is missing")
        );
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleMissingRequestPart(MissingServletRequestPartException ex) {
        ValidationErrorResponse payload = new ValidationErrorResponse(
                "Validation failed",
                Map.of(ex.getRequestPartName(), "Required multipart part is missing")
        );
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleTypeMismatch(Exception ex) {
        String parameter = "parameter";
        String expectedType = "supported type";

        if (ex instanceof MethodArgumentTypeMismatchException mismatchException) {
            parameter = mismatchException.getName();
            if (mismatchException.getRequiredType() != null) {
                expectedType = mismatchException.getRequiredType().getSimpleName();
            }
        }

        ValidationErrorResponse payload = new ValidationErrorResponse(
                "Validation failed",
                Map.of(parameter, "Invalid value. Expected " + expectedType)
        );
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", payload));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMalformedJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Malformed JSON request body", null));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Route not found: " + ex.getResourcePath(), null));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDatabaseError(DataAccessException ex) {
        String dbMessage = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        log.error("Database operation failed", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Database error: " + dbMessage, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        log.error("Unexpected server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Unexpected server error", null));
    }
}
