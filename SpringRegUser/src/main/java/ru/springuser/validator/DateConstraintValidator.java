package ru.springuser.validator;

import ru.springuser.annotation.DateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
    

public class DateConstraintValidator implements ConstraintValidator<DateConstraint, String> {
    @Override
    public void initialize(DateConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.isEmpty()) return false;
        return true;
    }
}
