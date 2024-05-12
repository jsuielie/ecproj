package com.ecproj.ECommercialProject.service.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ecproj.ECommercialProject.dto.request.auth.SignUpRequestDTO;
import com.ecproj.ECommercialProject.model.User;

@Mapper(componentModel = "spring") // 這個 Annotation 會告訴 MapStruct 生成一個實現類，並能由 Spring IoC 來管理
public interface AuthenticationMapper {
  AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

  @Mapping(target = "authorities", expression = "java( new java.util.HashSet<String>( java.util.Arrays.asList(\"ROLE_USER\")) )")
  @Mapping(target = "isLocked", constant = "false")
  @Mapping(target = "isEnabled", constant = "true")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User signUpRequestDTOToUser(SignUpRequestDTO signUpRequestDTO);
}
