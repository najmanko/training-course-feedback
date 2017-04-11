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

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingCourseForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TrainingCourseForm trainingCourseForm = (TrainingCourseForm) o;

        rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty");
        if (!StringUtils.isEmpty(errors.getFieldValue("emailAddress"))) {

            if (!isEmailAddressValid(trainingCourseForm.getEmailAddress())) {
                errors.rejectValue("emailAddress", "email.incorrect.format");
            }
        }
        rejectIfEmptyOrWhitespace(errors, "trainingCourse", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "trainingCourseDate", "NotEmpty");
    }

    private boolean isEmailAddressValid(String emailAddress) {
        Pattern pattern = compile(EMAIL_PATTERN, CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}
