package com.ecproj.ECommercialProject.validator.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecproj.ECommercialProject.dto.request.auth.LogInRequestDTO;
import com.ecproj.ECommercialProject.validator.annotation.ProviderCorrespondingFieldExistsValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LogInProviderCorrespondingFieldExistsValidator
    implements ConstraintValidator<ProviderCorrespondingFieldExistsValidation, LogInRequestDTO> {

  @Autowired
  private List<String> providers;

  @Override
  public void initialize(ProviderCorrespondingFieldExistsValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(LogInRequestDTO logInRequestDTO, ConstraintValidatorContext context) {
    if (logInRequestDTO.getProvider() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("provider").addConstraintViolation();
      return false;
    }
    if (!providers.contains(logInRequestDTO.getProvider().toLowerCase())) {
      context.buildConstraintViolationWithTemplate("should be one of " + String.join(", ", providers))
          .addPropertyNode("provider").addConstraintViolation();
      return false;
    }
    if (logInRequestDTO.getProvider().toLowerCase().equals("username") && logInRequestDTO.getUsername() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("username").addConstraintViolation();
      return false;
    }
    if (logInRequestDTO.getProvider().toLowerCase().equals("email") && logInRequestDTO.getEmail() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("email").addConstraintViolation();
      return false;
    }
    if (logInRequestDTO.getProvider().toLowerCase().equals("phone") && logInRequestDTO.getPhone() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("phone").addConstraintViolation();
      return false;
    }
    return true;
  }
}
