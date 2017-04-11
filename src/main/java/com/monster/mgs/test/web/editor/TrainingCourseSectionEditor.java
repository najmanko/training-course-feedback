package com.monster.mgs.test.web.editor;

import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.service.TrainingCourseService;

import java.beans.PropertyEditorSupport;

import static java.lang.Long.parseLong;
import static org.springframework.util.StringUtils.isEmpty;

public class TrainingCourseSectionEditor extends PropertyEditorSupport {

    private TrainingCourseService service;

    public TrainingCourseSectionEditor(TrainingCourseService service) {
        this.service = service;
    }

    @Override
    public void setAsText(String text) {
        TrainingCourseSection section = isEmpty(text) ? null : service.findSection(parseLong(text));
        this.setValue(section);
    }
}