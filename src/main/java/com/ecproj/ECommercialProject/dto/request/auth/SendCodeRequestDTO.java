package com.ecproj.ECommercialProject.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendCodeRequestDTO implements ProviderRequest{
  @NotNull(message = "required")
  private String provider;
  private String email;
  private String phone;
}
