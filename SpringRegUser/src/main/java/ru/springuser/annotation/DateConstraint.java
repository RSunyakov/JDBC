package ru.springuser.annotation;

import ru.springuser.validator.DateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateConstraintValidator.class)
public @interface DateConstraint  {
    String message() default "{Неверная дата}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}