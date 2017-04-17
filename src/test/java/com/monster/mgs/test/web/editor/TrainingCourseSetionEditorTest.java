package com.monster.mgs.test.web.editor;

import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.service.TrainingCourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TrainingCourseSetionEditorTest {

    @Mock
    private TrainingCourseService service;

    private TrainingCourseSectionEditor trainingCourseSectionEditor;
    
    @Before
    public void init() {
        trainingCourseSectionEditor = new TrainingCourseSectionEditor(service);
    }

    @Test
    public void setTrainingCourseSectionFromNull() {
        //when
        trainingCourseSectionEditor.setAsText(null);

        //then
        assertNull(trainingCourseSectionEditor.getValue());
    }

    @Test
    public void setTrainingCourseSectionFromEmpty() {
        //when
        trainingCourseSectionEditor.setAsText("");

        //then
        assertNull(trainingCourseSectionEditor.getValue());
    }

    @Test(expected=NumberFormatException.class)
    public void setTrainingCourseSectionFromNotNumber() {
        //when
        trainingCourseSectionEditor.setAsText("abc");

        //then
        assertNull(trainingCourseSectionEditor.getValue());
    }

    @Test
    public void setTrainingCourseSectionFromNumber() {
        //given
        TrainingCourseSection trainingCourseSection = new TrainingCourseSection();
        given(service.findSection(4L)).willReturn(trainingCourseSection);
        
        //when
        trainingCourseSectionEditor.setAsText("4");

        //then
        assertEquals(trainingCourseSectionEditor.getValue(), trainingCourseSection);
    }
}
