package com.ecproj.ECommercialProject.dto.request.auth;

import com.ecproj.ECommercialProject.enums.Gender;
import com.ecproj.ECommercialProject.validator.annotation.ProviderCorrespondingFieldExistsValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ProviderCorrespondingFieldExistsValidation
public class SignUpRequestDTO implements ProviderRequest{
  private String provider;

  @NotNull(message = "required")
  @Size(min = 6, max = 30, message = "password must be between 6 and 30 characters")
  @Pattern(regexp = "^[A-Za-z0-9]*$", message = "should be alphanumeric")
  private String password;

  @Size(min = 6, max = 30, message = "username must be between 4 and 20 characters")
  @Pattern(regexp = "^[A-Za-z0-9]*$", message = "should be alphanumeric")
  private String username;

  @Email
  private String email;

  @Size(min = 10, max = 10, message = "should be 10 digits")
  @Pattern(regexp = "^[0][0-9]{7,9}$", message = "should be the format of 0XXXXXXXXX")
  private String phone;
  private Gender gender;
}
