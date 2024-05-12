package com.ecproj.ECommercialProject.validator.annotation;

import java.lang.annotation.Target;

import com.ecproj.ECommercialProject.validator.implementation.LogInProviderCorrespondingFieldExistsValidator;
import com.ecproj.ECommercialProject.validator.implementation.SignUpProviderCorrespondingFieldExistsValidator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 該註解可以被使用在 SignUpRequestDTO 與 LogInRequestDTO 類別上
@Constraint(validatedBy = { SignUpProviderCorrespondingFieldExistsValidator.class,
    LogInProviderCorrespondingFieldExistsValidator.class })
public @interface ProviderCorrespondingFieldExistsValidation {
  String message() default "required";

  Class<?>[] groups() default {};

  Class<? extends jakarta.validation.Payload>[] payload() default {};
}
