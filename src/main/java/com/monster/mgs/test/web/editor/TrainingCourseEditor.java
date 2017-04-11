package com.monster.mgs.test.web.editor;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.service.TrainingCourseService;

import java.beans.PropertyEditorSupport;

import static java.lang.Long.parseLong;
import static org.springframework.util.StringUtils.isEmpty;

public class TrainingCourseEditor extends PropertyEditorSupport {

    private TrainingCourseService service;

    public TrainingCourseEditor(TrainingCourseService service) {
        this.service = service;
    }

    @Override
    public void setAsText(String text) {
        TrainingCourse course = isEmpty(text) ? null : service.findCourse(parseLong(text));
        super.setValue(course);
    }
}