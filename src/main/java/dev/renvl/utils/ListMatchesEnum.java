package dev.renvl.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumListValidator.class)
public @interface ListMatchesEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default "List elements do not match the enum values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}