package com.monster.mgs.test.web;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.service.TrainingCourseService;
import com.monster.mgs.test.web.editor.TrainingCourseEditor;
import com.monster.mgs.test.web.editor.TrainingCourseSectionEditor;
import com.monster.mgs.test.web.validator.Step1Validator;
import com.monster.mgs.test.web.validator.Step2Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@SessionAttributes("trainingCourseForm")
public class TrainingCourseController {

    @Autowired
    private Step1Validator step1Validator;

    @Autowired
    private Step2Validator step2Validator;
    
    @Autowired
    private TrainingCourseService service;

    @RequestMapping(value = "/", method = GET)
    public String welcome() {
        return "index";
    }

    @RequestMapping(value="/clearStep1")
    public ModelAndView clearStep1() {
        return getModelForStep1(new TrainingCourseForm());
    }

    @RequestMapping(value = "/showStep1", method = POST)
    public ModelAndView showStep1(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        return getModelForStep1(trainingCourseForm);
    }

    @RequestMapping(value = "/processStep1")
    public ModelAndView processStep1(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm,
                              BindingResult bindingResult) {
        step1Validator.validate(trainingCourseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return getModelForStep1(trainingCourseForm);
        }
        return getModelForStep2(trainingCourseForm);
    }

    @RequestMapping(value = "/processStep2", method = POST)
    public ModelAndView processStep2(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm,
                              BindingResult bindingResult) {
        step2Validator.validate(trainingCourseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return getModelForStep2(trainingCourseForm);
        }
        return new ModelAndView("step3", "trainingCourseForm", trainingCourseForm);
    }

    @RequestMapping(value = "/processStep3", method = POST)
    public ModelAndView showTable(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        Long feedbackId = service.saveOrUpdateTrainingCourse(trainingCourseForm);
        List<TrainingCourseFeedback> feedbacks = service.getFeedbacks();
        ModelAndView model = new ModelAndView("feedbacks", "feedbacks", feedbacks);
        model.addObject("statusMessage", getSavedStatusMessage(feedbackId));
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TrainingCourse.class, "trainingCourse", new TrainingCourseEditor(service));
        binder.registerCustomEditor(TrainingCourseSection.class, "favorite", new TrainingCourseSectionEditor(service));
    }

    private ModelAndView getModelForStep1(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        ModelAndView model = new ModelAndView("step1", "trainingCourseForm", trainingCourseForm);
        model.addObject("listOfTrainingCourses", service.getCourses());
        return model;
    }
    
    private ModelAndView getModelForStep2(TrainingCourseForm trainingCourseForm) {
        ModelAndView model = new ModelAndView("step2", "trainingCourseForm", trainingCourseForm);
        List<TrainingCourseSection> sections = service.getSections(trainingCourseForm.getTrainingCourse());
        model.addObject("sections", sections);
        return model;
    }
    
    private String getSavedStatusMessage(Long feedbackId) {
        if (feedbackId != null) {
            return format("Feedback with id: %s saved successfully.", feedbackId);
        } 
        return "Feedback was not saved!";
    }
}