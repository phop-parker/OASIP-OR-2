package com.oasip.oasipservices.validators;

import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeOverlappingValidator.class)
@Documented
public @interface TimeOverlapping{
    String message() default "event start time is overlapping";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
