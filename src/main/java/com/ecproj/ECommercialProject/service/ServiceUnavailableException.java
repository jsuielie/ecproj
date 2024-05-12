package com.ecproj.ECommercialProject.service;

public class ServiceUnavailableException extends RuntimeException{
  private String service;
  private int code = 404;

  public ServiceUnavailableException(String service, String message) {
    super(message);
    this.service = service;
  }

  public ServiceUnavailableException(String service, int code, String message) {
    super(message);
    this.service = service;
    this.code = code;
  }

  public String getService() {
    return service;
  }

  public int getCode() {
    return code;
  }
}
