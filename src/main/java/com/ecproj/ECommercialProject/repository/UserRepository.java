package com.ecproj.ECommercialProject.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecproj.ECommercialProject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
  Optional<User> findByPhone(String phone);
  Optional<User> findByEmail(String email);
  Boolean existsByUsername(String username);
}
