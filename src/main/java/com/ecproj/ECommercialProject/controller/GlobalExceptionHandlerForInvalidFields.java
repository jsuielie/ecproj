package com.ecproj.ECommercialProject.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecproj.ECommercialProject.dto.response.GeneralExceptionResponseDTO;
import com.ecproj.ECommercialProject.dto.response.InvalidFieldsResponseDTO;
import com.ecproj.ECommercialProject.service.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandlerForInvalidFields {
  // 捕捉 RequestBody 驗證錯誤 
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<InvalidFieldsResponseDTO> handleValidationExceptions(MethodArgumentNotValidException e) {
    Map<String, String> fieldErrorMap = e.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.groupingBy(FieldError::getField,
            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())))
        .entrySet().stream().collect(
            Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
    InvalidFieldsResponseDTO invalidFieldsResponseDTO = new InvalidFieldsResponseDTO();
    invalidFieldsResponseDTO.setFields(fieldErrorMap);
    return ResponseEntity.unprocessableEntity().body(invalidFieldsResponseDTO);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<GeneralExceptionResponseDTO> handleBadCredentialsException(BadCredentialsException e) {
    GeneralExceptionResponseDTO badCredentialsResponseDTO = new GeneralExceptionResponseDTO();
    badCredentialsResponseDTO.setMessage("Forbidden");
    return new ResponseEntity<>(badCredentialsResponseDTO, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<GeneralExceptionResponseDTO> handleServiceUnavailableException(ServiceUnavailableException e) {
    GeneralExceptionResponseDTO serviceUnavailableResponseDTO = new GeneralExceptionResponseDTO();
    serviceUnavailableResponseDTO.setMessage(e.getService().toUpperCase() + " " + e.getMessage());
    return new ResponseEntity<>(serviceUnavailableResponseDTO, HttpStatus.BAD_REQUEST);
  }

  // 捕捉資料庫所拋出的完整性例外
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<GeneralExceptionResponseDTO> handleDataIntegrityViolationException(
      DataIntegrityViolationException e) {
    GeneralExceptionResponseDTO dataIntegrityViolationResponseDTO = new GeneralExceptionResponseDTO();
    Throwable cause = e.getRootCause();
    if (cause instanceof SQLException) {
      SQLException sqlException = (SQLException) cause;
      int code = sqlException.getErrorCode();
      if (code == 23505) {
        dataIntegrityViolationResponseDTO.setMessage("duplicated");
      } else {
        dataIntegrityViolationResponseDTO.setMessage("not yet handled"); // 尚未處理的錯誤
      }
    }
    return ResponseEntity.unprocessableEntity().body(dataIntegrityViolationResponseDTO);
  }
}
