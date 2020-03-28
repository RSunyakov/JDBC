package ru.springuser.annotation;

import ru.springuser.validator.EmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailConstraintValidator.class)
public @interface EmailConstraint  {
    String message() default "{Неверный email}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}