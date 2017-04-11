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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes("trainingCourseForm")
public class TrainingCourseController {

    @Autowired
    private Step1Validator step1Validator;

    @Autowired
    private Step2Validator step2Validator;
    
    @Autowired
    private TrainingCourseService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "index";
    }

    @RequestMapping(value="/clearStep1")
    public ModelAndView clearStep1() {
        return prepareStep1(new TrainingCourseForm());
    }

    @RequestMapping(value = "/prepareStep1")
    public ModelAndView prepareStep1(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        return getModelForStep1(trainingCourseForm);
    }

    @RequestMapping(value = "/processStep1")
    public ModelAndView step2(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm,
                              BindingResult bindingResult) {
        step1Validator.validate(trainingCourseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return getModelForStep1(trainingCourseForm);
        }
        return getModelForStep2(trainingCourseForm);
    }

    @RequestMapping(value = "/processStep2")
    public ModelAndView processStep2(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm,
                              BindingResult bindingResult) {
        step2Validator.validate(trainingCourseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return getModelForStep2(trainingCourseForm);
        }
        return new ModelAndView("step3", "trainingCourseForm", trainingCourseForm);
    }

    @RequestMapping(value = "/processStep3")
    public ModelAndView showTable(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        service.saveOrUpdateTrainingCourse(trainingCourseForm);
        List<TrainingCourseFeedback> feedbacks = service.getFeedbacks();
        return new ModelAndView("feedbacks", "feedbacks", feedbacks);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TrainingCourse.class, "trainingCourse", new TrainingCourseEditor(service));
        binder.registerCustomEditor(TrainingCourseSection.class, "favorite", new TrainingCourseSectionEditor(service));
    }

    private ModelAndView getModelForStep1(@ModelAttribute("trainingCourseForm") TrainingCourseForm trainingCourseForm) {
        ModelAndView model = new ModelAndView("step1", "trainingCourseForm", trainingCourseForm);
        model.addObject("listOfTrainingCourses", service.getAllCourses());
        return model;
    }
    
    private ModelAndView getModelForStep2(TrainingCourseForm trainingCourseForm) {
        ModelAndView model = new ModelAndView("step2", "trainingCourseForm", trainingCourseForm);
        List<TrainingCourseSection> sections = service.getSections(trainingCourseForm.getTrainingCourse());
        model.addObject("sections", sections);
        return model;
    }
}