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
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class Step2ValidatorTest {

    private static final String EMPTY = " ";

    private Validator validator = new Step2Validator();
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
        assertNull(errors.getFieldError("rate"));
        assertNull(errors.getFieldError("favorite"));
        assertNull(errors.getFieldError("comment"));
    }

    @Test
    public void formIsInvalidBecauseOfNullFields() {
        //given
        form.setFavorite(null);
        form.setRate(null);
        form.setComment(null);

        //when
        ValidationUtils.invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("rate").getRejectedValue(), null);
        assertEquals(errors.getFieldError("favorite").getRejectedValue(), null);
        assertEquals(errors.getFieldError("comment").getRejectedValue(), null);
    }

    @Test
    public void formIsInvalidBecauseOfCommentIsEmpty() {
        //given
        form.setComment(EMPTY);

        //when
        ValidationUtils.invokeValidator(validator, form, errors);

        //then
        assertEquals(errors.getFieldError("comment").getRejectedValue(), EMPTY);
        assertNull(errors.getFieldError("rate"));
        assertNull(errors.getFieldError("favorite"));
    }
}
