package com.ecproj.ECommercialProject.service.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ecproj.ECommercialProject.dto.request.auth.LogInRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SendCodeRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;
import com.ecproj.ECommercialProject.service.ServiceUnavailableException;

@Service("username")
public class UsernameAuthenticationService extends AuthenticationStrategy {

  @Override
  public void sendCode(SendCodeRequestDTO sendCodeRequestDTO) {
    throw new ServiceUnavailableException("Username", 400, "Not Supported In Sending Code");
  }

  @Override
  public void signup(SignUpRequestDTO signUpRequestDTO) {
    String username = signUpRequestDTO.getUsername();
    String password = signUpRequestDTO.getPassword();
    super.userInfoService.addUser(authenticationMapper.signUpRequestDTOToUser(signUpRequestDTO));

    // 自動將註冊的使用者登入
    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    Authentication authResult = super.authenticationManager.authenticate(authRequest);
    super.successfulAuthentication(authResult);
  }

  @Override
  public void login(LogInRequestDTO logInRequestDTO) {
    String username = logInRequestDTO.getUsername();
    username = (username != null) ? username.trim() : null;
    String password = logInRequestDTO.getPassword();
    password = (password != null) ? password.trim() : null;
    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    Authentication authResult = super.authenticationManager.authenticate(authRequest);
    super.successfulAuthentication(authResult);
  }
}
