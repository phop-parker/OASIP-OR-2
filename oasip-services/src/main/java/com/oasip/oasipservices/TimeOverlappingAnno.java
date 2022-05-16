package com.oasip.oasipservices;

import org.hibernate.annotations.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Documented
//@Constraint(validatedBy = TimeOverlappingValidator.class)
//@Target( { ElementType.METHOD, ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface TimeOverlappingAnno {
//    String message() default "event start time is overlaping";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
