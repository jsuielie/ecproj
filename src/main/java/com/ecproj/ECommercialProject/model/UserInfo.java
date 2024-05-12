package com.ecproj.ECommercialProject.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  private boolean isLocked;
  private boolean isEnabled;

  public UserInfo(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.authorities = AuthorityUtils
        .createAuthorityList(user.getAuthorities());
    this.isLocked = user.isLocked();
    this.isEnabled = user.isEnabled();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.isLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
}
