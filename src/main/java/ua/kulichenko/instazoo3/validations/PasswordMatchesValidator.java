package ua.kulichenko.instazoo3.validations;


import ua.kulichenko.instazoo3.annotations.PasswordMatches;
import ua.kulichenko.instazoo3.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }



    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
