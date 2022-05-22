package com.oasip.oasipservices.validation;

import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConstraintNameDateValidator.class)
@Documented

public @interface ConstraintNameDate{
    String message() default "Booking name and Event start time can't be same";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
