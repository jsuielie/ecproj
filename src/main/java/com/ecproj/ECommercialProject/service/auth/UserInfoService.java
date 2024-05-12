package com.ecproj.ECommercialProject.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecproj.ECommercialProject.model.User;
import com.ecproj.ECommercialProject.model.UserInfo;
import com.ecproj.ECommercialProject.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException("User not found");
    }
    return new UserInfo(userRepository.findByUsername(username).get());
  }

  public void addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

}
