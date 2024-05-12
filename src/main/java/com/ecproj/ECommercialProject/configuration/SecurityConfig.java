package com.ecproj.ECommercialProject.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ecproj.ECommercialProject.enums.Provider;
import com.ecproj.ECommercialProject.service.auth.AuthenticationContext;
import com.ecproj.ECommercialProject.service.auth.AuthenticationStrategy;
import com.ecproj.ECommercialProject.service.auth.UserInfoService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserInfoService userInfoService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public List<String> getProviders() {
    return Arrays.stream(Provider.values()).map(Provider::name).map(String::toLowerCase).collect(Collectors.toList());
  }

  @Autowired
  private Map<String, AuthenticationStrategy> strategyMap;

  @Bean
  public AuthenticationContext getAuthenticationContext() {
    return new AuthenticationContext(strategyMap);
  }

  @Bean
  public AuthenticationManager providerManager(PasswordEncoder passwordEncoder)
      throws Exception {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userInfoService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable());
    http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // 關閉 X-Frame-Options（避免 H2
                                                                                           // Console 無法載入）
    http.logout(logout -> logout.logoutUrl("/api/v1/auth/logout")); // 客製化登出路徑，使得 logoutFilter
                                                                 // 在請求發送至此路徑時觸發；觸發該過濾器後，後端會發送轉址回應，要求轉導向去
                                                                 // logoutSuccessUrl
    http.authorizeHttpRequests(requests -> requests
        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login", "api/v1/auth/logout/success").permitAll()
        .anyRequest().authenticated());
    http.logout(logout -> logout.logoutSuccessUrl("/api/v1/auth/logout/success")); // 定義登出過濾器觸發後產生的回應，其所要求的路徑

    return http.build();
  }
}
