package com.ecproj.ECommercialProject.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.ecproj.ECommercialProject.dto.request.auth.LogInRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SendCodeRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AuthenticationStrategy {

  @Autowired
  protected UserInfoService userInfoService;

  @Autowired
  protected AuthenticationManager authenticationManager;

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected AuthenticationMapper authenticationMapper;

  private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
      .getContextHolderStrategy();
  private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private HttpServletResponse response;

  // 參考自
  // https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/authentication/AbstractAuthenticationProcessingFilter.java
  protected void successfulAuthentication(Authentication authResult) {
    SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authResult);
    this.securityContextHolderStrategy.setContext(context);
    this.securityContextRepository.saveContext(context, request, response);
  }

  // 參考自
  // https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/authentication/AbstractAuthenticationProcessingFilter.java
  protected void unsuccessfulAuthentication() {
    this.securityContextHolderStrategy.clearContext();
  }

  public abstract void sendCode(SendCodeRequestDTO sendCodeRequestDTO);

  public abstract void signup(SignUpRequestDTO signUpRequestDTO);

  public abstract void login(LogInRequestDTO logInRequestDTO);

}
