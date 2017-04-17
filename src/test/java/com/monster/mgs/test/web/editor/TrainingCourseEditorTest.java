package com.monster.mgs.test.web.editor;

import com.monster.mgs.test.model.TrainingCourse;
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
public class TrainingCourseEditorTest {

    @Mock
    private TrainingCourseService service;

    private TrainingCourseEditor trainingCourseEditor;
    
    @Before
    public void init() {
        trainingCourseEditor = new TrainingCourseEditor(service);
    }

    @Test
    public void setTrainingCourseFromNull() {
        //when
        trainingCourseEditor.setAsText(null);

        //then
        assertNull(trainingCourseEditor.getValue());
    }

    @Test
    public void setTrainingCourseFromEmpty() {
        //when
        trainingCourseEditor.setAsText("");

        //then
        assertNull(trainingCourseEditor.getValue());
    }

    @Test(expected=NumberFormatException.class)
    public void setTrainingCourseFromNotNumber() {
        //when
        trainingCourseEditor.setAsText("abc");

        //then
        assertNull(trainingCourseEditor.getValue());
    }

    @Test
    public void setTrainingCourseFromNumber() {
        //given
        TrainingCourse trainingCourse = new TrainingCourse();
        given(service.findCourse(4L)).willReturn(trainingCourse);
        
        //when
        trainingCourseEditor.setAsText("4");

        //then
        assertEquals(trainingCourseEditor.getValue(), trainingCourse);
    }
}
