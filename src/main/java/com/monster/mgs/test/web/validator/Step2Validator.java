package com.monster.mgs.test.web.validator;

import com.monster.mgs.test.web.TrainingCourseForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class Step2Validator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingCourseForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, "favorite", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "rate", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "comment", "NotEmpty");
    }
}
