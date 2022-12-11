package ua.kulichenko.instazoo3.validations;

import ua.kulichenko.instazoo3.annotations.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidators implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAL_PATERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";


    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return (validateEmail(email));
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAL_PATERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
