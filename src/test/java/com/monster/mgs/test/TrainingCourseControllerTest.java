package com.monster.mgs.test;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.service.TrainingCourseService;
import com.monster.mgs.test.web.TrainingCourseController;
import com.monster.mgs.test.web.TrainingCourseForm;
import com.monster.mgs.test.web.validator.Step1Validator;
import com.monster.mgs.test.web.validator.Step2Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.monster.mgs.test.util.DataGenerator.generateFeedback;
import static com.monster.mgs.test.util.DataGenerator.generateTrainingCourseForm;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@WebAppConfiguration
public class TrainingCourseControllerTest {

    private TrainingCourseForm form;
    private MockMvc mockMvc;

    @Mock
    private Step1Validator step1Validator;

    @Mock
    private Step2Validator step2Validator;

    @Mock
    private TrainingCourseService service;

    @InjectMocks
    private TrainingCourseController controller;

    @Before
    public void setup() {
        initMocks(this);
        mockMvc = standaloneSetup(controller).build();
        form = generateTrainingCourseForm();
    }

    @Test
    public void renderIndexPage() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/"));

        //then
        Mockito.verifyZeroInteractions(service);
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("index"));
    }

    @Test
    public void renderStep1WithEmptyForm() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/clearStep1"));

        //then
        verify(service, times(1)).getCourses();
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("step1"))
                .andExpect(forwardedUrl("step1"))
                .andExpect(model().attribute("trainingCourseForm", isA(TrainingCourseForm.class)))
                .andExpect(model().attribute("trainingCourseForm", allOf(
                        hasProperty("firstName", nullValue()),
                        hasProperty("lastName", nullValue()),
                        hasProperty("emailAddress", nullValue()),
                        hasProperty("trainingCourseDate", nullValue()),
                        hasProperty("rate", nullValue()),
                        hasProperty("comment", nullValue()),
                        hasProperty("firstName", nullValue())
                )));
    }

    @Test
    public void renderStep1WithFilledForm() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/showStep1")
                .sessionAttr("trainingCourseForm", form));

        //then
        verify(service, times(1)).getCourses();
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("step1"))
                .andExpect(forwardedUrl("step1"));
        checkFormValues(resultActions);
    }

    @Test
    public void renderStep2() throws Exception {
        //given
        given(service.getSections(any(TrainingCourse.class))).willReturn(asList(form.getFavorite()));

        //when
        ResultActions resultActions = mockMvc.perform(post("/processStep1")
                .sessionAttr("trainingCourseForm", form));

        //then
        verify(service, times(1)).getSections(any(TrainingCourse.class));
        verify(step1Validator, times(1)).validate(any(Object.class), any(Errors.class));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("step2"))
                .andExpect(forwardedUrl("step2"));
        checkFormValues(resultActions);
    }

    @Test
    public void renderStep1WhenInvalidForm() throws Exception {
        //given
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        
        //when
        ModelAndView view = controller.processStep1(form, bindingResult);
        
        //then
        assertEquals(view.getViewName(), "step1");
    }

    @Test
    public void renderStep3() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(post("/processStep2")
                .sessionAttr("trainingCourseForm", form));

        //then
        verify(step2Validator, times(1)).validate(any(Object.class), any(Errors.class));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("step3"))
                .andExpect(forwardedUrl("step3"));
        checkFormValues(resultActions);
    }

    @Test
    public void renderStep2WhenInvalidForm() throws Exception {
        //given
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        ModelAndView view = controller.processStep2(form, bindingResult);

        //then
        assertEquals(view.getViewName(), "step2");
    }

    @Test
    public void renderFeedbacksPage() throws Exception {
        //given
        TrainingCourseFeedback feedback = generateFeedback();
        given(service.getFeedbacks()).willReturn(asList(feedback));

        //when
        ResultActions resultActions = mockMvc.perform(post("/processStep3")
                .sessionAttr("trainingCourseForm", form));

        //then
        verify(service, times(1)).getFeedbacks();
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("feedbacks"))
                .andExpect(forwardedUrl("feedbacks"))
                .andExpect(model().attribute("feedbacks", isA(List.class)))
                .andExpect(model().attribute("feedbacks", hasSize(1)))
                .andExpect(model().attribute("feedbacks", hasItem(
                        allOf(
                                hasProperty("visitor",
                                        allOf(
                                                hasProperty("firstName", is(feedback.getVisitor().getFirstName())),
                                                hasProperty("lastName", is(feedback.getVisitor().getLastName())),
                                                hasProperty("emailAddress", is(feedback.getVisitor().getEmailAddress()))
                                        )),
                                hasProperty("date", is(feedback.getDate())),
                                hasProperty("rating", is(feedback.getRating())),
                                hasProperty("comment", is(feedback.getComment())),
                                hasProperty("favoriteSection", is(feedback.getFavoriteSection())),
                                hasProperty("trainingCourse", is(feedback.getTrainingCourse()))
                        )
                )));
    }

    private void checkFormValues(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(model().attribute("trainingCourseForm", isA(TrainingCourseForm.class)))
                .andExpect(model().attribute("trainingCourseForm", allOf(
                        hasProperty("firstName", is(form.getFirstName())),
                        hasProperty("lastName", is(form.getLastName())),
                        hasProperty("emailAddress", is(form.getEmailAddress())),
                        hasProperty("trainingCourseDate", is(form.getTrainingCourseDate())),
                        hasProperty("trainingCourse", is(form.getTrainingCourse())),
                        hasProperty("favorite", is(form.getFavorite())),
                        hasProperty("rate", is(form.getRate())),
                        hasProperty("comment", is(form.getComment()))
                )));
    }
}
