package com.ecproj.ECommercialProject.controller.api.v1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecproj.ECommercialProject.dto.request.auth.LogInRequestDTO;
import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;
import com.ecproj.ECommercialProject.service.auth.AuthenticationContext;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = "application/json")
public class AuthenticationController {

  @Autowired
  private AuthenticationContext authenticationContext;

  @PostMapping(path = "/signup", consumes = "application/json")
  public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequestDTO signupRequestDto) {
    authenticationContext.signup(signupRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping(path = "/login", consumes = "application/json")
  public ResponseEntity<Void> login(@Valid @RequestBody LogInRequestDTO logInRequestDTO) {
    authenticationContext.login(logInRequestDTO);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(path = "/logout/success")
  public ResponseEntity<Void> logout() {
    return ResponseEntity.noContent().build();
  }
}
