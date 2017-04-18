package com.monster.mgs.test.web.validator;

import com.monster.mgs.test.web.TrainingCourseForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.monster.mgs.test.util.DataGenerator.generateTrainingCourseForm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.validation.ValidationUtils.invokeValidator;

@RunWith(MockitoJUnitRunner.class)
public class Step1ValidatorTest {

    private static final String EMPTY = " ";
    private static final String STRING_WITH_51_CHARS = "123456789012345678921234567893123456789412345678951";
    private static final String STRING_WITH_200_CHARS = "12345678901234567892123456789312345678941234567895" +
            "12345678961234567897123456789812345678991234567890" +
            "12345678901234567892123456789312345678941234567895" +
            "123456789612345678971234567898123456789912345678901";

    private Validator validator = new Step1Validator();
    private TrainingCourseForm form;
    private Errors errors;

    @Before
    public void setUp() {
        form = generateTrainingCourseForm();
        errors = new BeanPropertyBindingResult(form, "trainingCourseForm");
    }

    @Test
    public void formIsValid() {
        //when
        invokeValidator(validator, form, errors);

        //then
        assertNull(errors.getFieldError("firstName"));
        assertNull(errors.getFieldError("lastName"));
        assertNull(errors.getFieldError("emailAddress"));
        assertNull(errors.getFieldError("trainingCourse"));
        assertNull(errors.getFieldError("trainingCourseDate"));
    }

    @Test
    public void formIsInvalidBecauseOfNullFields() {
        //given
        form.setFirstName(null);
        form.setLastName(null);
        form.setEmailAddress(null);
        form.setTrainingCourse(null);
        form.setTrainingCourseDate(null);

        //when
        invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("firstName").getRejectedValue(), null);
        assertEquals(errors.getFieldError("lastName").getRejectedValue(), null);
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), null);
        assertEquals(errors.getFieldError("trainingCourse").getRejectedValue(), null);
        assertEquals(errors.getFieldError("trainingCourseDate").getRejectedValue(), null);
    }

    @Test
    public void formIsInvalidBecauseOfEmptyFields() {
        formValuesValidation(EMPTY, EMPTY);
    }

    @Test
    public void formIsInvalidBecauseOfTooLongFields() {
        formValuesValidation(STRING_WITH_51_CHARS, STRING_WITH_200_CHARS);
    }
    
    private void formValuesValidation(String namesValue, String emailValue) {
        //given
        form.setFirstName(namesValue);
        form.setLastName(namesValue);
        form.setEmailAddress(emailValue);

        //when
        invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("firstName").getRejectedValue(), namesValue);
        assertEquals(errors.getFieldError("lastName").getRejectedValue(), namesValue);
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), emailValue);
    }

    @Test
    public void formIsInvalidBecauseOfInvalidEmail() {
        //given
        form.setEmailAddress("invalidMail");

        //when
        invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), "invalidMail");
    }
}
