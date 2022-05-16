package com.oasip.oasipservices;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

//public class TimeOverlappingValidator implements ConstraintValidator<TimeOverlappingAnno, String> {
//
//        @Override
//        public void initialize(TimeOverlappingAnno eventStartTime) {
//        }
//
//        @Override
//        public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
//            return contactField != null && contactField.matches("[0-9]+")
//                    && (contactField.length() > 8) && (contactField.length() < 14);
//        }
//}
