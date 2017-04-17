package com.monster.mgs.test.web.validator;

import com.monster.mgs.test.web.TrainingCourseForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.monster.mgs.test.util.DataGenerator.generateTrainingCourseForm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class Step1ValidatorTest {
    
    private static final String EMPTY = " ";

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
        ValidationUtils.invokeValidator(validator, form, errors);

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
        ValidationUtils.invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("firstName").getRejectedValue(), null);
        assertEquals(errors.getFieldError("lastName").getRejectedValue(), null);
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), null);
        assertEquals(errors.getFieldError("trainingCourse").getRejectedValue(), null);
        assertEquals(errors.getFieldError("trainingCourseDate").getRejectedValue(), null);
    }

    @Test
    public void formIsInvalidBecauseOfEmptyFields() {
        //given
        form.setFirstName(EMPTY);
        form.setLastName(EMPTY);
        form.setEmailAddress(EMPTY);

        //when
        ValidationUtils.invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("firstName").getRejectedValue(), EMPTY);
        assertEquals(errors.getFieldError("lastName").getRejectedValue(), EMPTY);
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), EMPTY);
    }

    @Test
    public void formIsInvalidBecauseOfInvalidEmail() {
        //given
        form.setEmailAddress("invalidMail");

        //when
        ValidationUtils.invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("emailAddress").getRejectedValue(), "invalidMail");
    }
}
