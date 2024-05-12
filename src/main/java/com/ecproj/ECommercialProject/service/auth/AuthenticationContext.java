package com.ecproj.ECommercialProject.service.auth;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecproj.ECommercialProject.dto.request.auth.LogInRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.ProviderRequest;
import com.ecproj.ECommercialProject.dto.request.auth.SendCodeRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;
import com.ecproj.ECommercialProject.service.ServiceUnavailableException;

@Service
public class AuthenticationContext {

  private boolean isAuthenticationServiceAvailable = true;
  private Map<String, AuthenticationStrategy> strategyMap;

  // 透過建構子注入驗證策略 Map
  public AuthenticationContext(Map<String, AuthenticationStrategy> strategyMap) {
    this.strategyMap = strategyMap;
  }

  private AuthenticationStrategy getAuthenticationService(ProviderRequest providerRequestDTO) {
    if (!isAuthenticationServiceAvailable) {
      throw new ServiceUnavailableException("Authentication Service", 400, "Not Available");
    }
    String provider = providerRequestDTO.getProvider().toLowerCase();
    if (!strategyMap.containsKey(provider)) {
      throw new ServiceUnavailableException(providerRequestDTO.getProvider(), 400, "Not Available");
    }
    return strategyMap.get(provider);
  }

  public void sendCode(SendCodeRequestDTO sendCodeRequestDTO) {
    AuthenticationStrategy authenticationService = getAuthenticationService(sendCodeRequestDTO);
    authenticationService.sendCode(sendCodeRequestDTO);
  }

  public void signup(SignUpRequestDTO signUpRequestDTO) {
    AuthenticationStrategy authenticationService = getAuthenticationService(signUpRequestDTO);
    authenticationService.signup(signUpRequestDTO);
  }

  public void login(LogInRequestDTO logInRequestDTO) {
    AuthenticationStrategy authenticationService = getAuthenticationService(logInRequestDTO);
    authenticationService.login(logInRequestDTO);
  }

}
