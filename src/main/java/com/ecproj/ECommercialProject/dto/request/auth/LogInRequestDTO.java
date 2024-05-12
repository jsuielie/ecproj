package com.ecproj.ECommercialProject.dto.request.auth;

import com.ecproj.ECommercialProject.validator.annotation.ProviderCorrespondingFieldExistsValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ProviderCorrespondingFieldExistsValidation
public class LogInRequestDTO implements ProviderRequest{
  private String provider;
  private String username;
  private String email;
  private String phone;
  @NotNull(message = "required")
  @NotBlank(message = "required")
  private String password;
}
