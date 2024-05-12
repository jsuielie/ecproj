package com.ecproj.ECommercialProject.dto.response;

import java.util.Map;

import lombok.Data;

@Data
public class InvalidFieldsResponseDTO {
  private String message = "Invalid Fields";
  private Map<String, String> fields;
}
