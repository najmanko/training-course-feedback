package com.monster.mgs.test.web.validator;

import com.monster.mgs.test.web.TrainingCourseForm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class Step1Validator implements Validator {

    private static final String EMAIL_PATTERN = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    private static final int EMAIL_MAX_LENGTH = 200;
    private static final int NAME_MAX_LENGTH = 50;

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingCourseForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TrainingCourseForm form = (TrainingCourseForm) o;

        validateName(form, errors);
        validateEmail(form.getEmailAddress(), errors);
        
        rejectIfEmptyOrWhitespace(errors, "trainingCourse", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "trainingCourseDate", "NotEmpty");
    }
    
    private void validateName(TrainingCourseForm form, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (!StringUtils.isEmpty(errors.getFieldValue("firstName"))) {
            if (form.getFirstName().length() > NAME_MAX_LENGTH) {
                errors.rejectValue("firstName", "name.too.long");
            }
        }
        rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (!StringUtils.isEmpty(errors.getFieldValue("lastName"))) {
            if (form.getFirstName().length() > NAME_MAX_LENGTH) {
                errors.rejectValue("lastName", "name.too.long");
            }
        }
    }
    
    private void validateEmail(String email, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty");
        if (!StringUtils.isEmpty(errors.getFieldValue("emailAddress"))) {
            if (email.length() > EMAIL_MAX_LENGTH) {
                errors.rejectValue("emailAddress", "email.too.long");
            }
            if (!isEmailAddressValid(email)) {
                errors.rejectValue("emailAddress", "email.incorrect.format");
            }
        }
    }

    private boolean isEmailAddressValid(String emailAddress) {
        Pattern pattern = compile(EMAIL_PATTERN, CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}
