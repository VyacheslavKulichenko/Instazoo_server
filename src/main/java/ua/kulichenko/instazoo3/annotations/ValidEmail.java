package ua.kulichenko.instazoo3.annotations;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import ua.kulichenko.instazoo3.validations.EmailValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidators.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid Email";
            Class<?>[] groups() default {};
            Class<? extends Payload>[] payload() default {};

}
