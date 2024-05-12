package com.ecproj.ECommercialProject.validator.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;
import com.ecproj.ECommercialProject.validator.annotation.ProviderCorrespondingFieldExistsValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SignUpProviderCorrespondingFieldExistsValidator
    implements ConstraintValidator<ProviderCorrespondingFieldExistsValidation, SignUpRequestDTO> {

  @Autowired
  private List<String> providers;

  @Override
  public void initialize(ProviderCorrespondingFieldExistsValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(SignUpRequestDTO signUpRequestDTO, ConstraintValidatorContext context) {
    if (signUpRequestDTO.getProvider() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("provider").addConstraintViolation();
      return false;
    }
    if (!providers.contains(signUpRequestDTO.getProvider().toLowerCase())) {
      context.buildConstraintViolationWithTemplate("should be one of " + String.join(", ", providers))
          .addPropertyNode("provider").addConstraintViolation();
      return false;
    }
    if (signUpRequestDTO.getProvider().toLowerCase().equals("username") && signUpRequestDTO.getUsername() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("username").addConstraintViolation();
      return false;
    }
    if (signUpRequestDTO.getProvider().toLowerCase().equals("email") && signUpRequestDTO.getEmail() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("email").addConstraintViolation();
      return false;
    }
    if (signUpRequestDTO.getProvider().toLowerCase().equals("phone") && signUpRequestDTO.getPhone() == null) {
      context.buildConstraintViolationWithTemplate("required").addPropertyNode("phone").addConstraintViolation();
      return false;
    }
    return true;
  }
}
