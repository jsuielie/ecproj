package com.ecproj.ECommercialProject.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users") // 將 User 資料表名稱改為 users（user 為資料庫關鍵字）
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID) // JPA 版本 3.1.0 以上才支援
  private UUID id;

  @Column(unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(unique = true)
  private String email;
  private String phone;

  private Set<String> authorities;
  private boolean isLocked;
  private boolean isEnabled;
  @Column(insertable = false, updatable = false)
  @CreationTimestamp(source = SourceType.DB) // 由資料庫產生建立時間
  private Instant createdAt;
  @Column(insertable = false, updatable = false)
  @UpdateTimestamp(source = SourceType.DB) // 由資料庫產生更新時間
  private Instant updatedAt;

  @PrePersist
  public void prePersist() {
    if (!this.isEnabled) {
      this.isEnabled = true;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return id != null && id.equals(user.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
