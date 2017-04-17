package com.monster.mgs.test.service;

import com.monster.mgs.test.dao.TrainingCourseDao;
import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.web.TrainingCourseForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrainingCourseServiceTest {

    @Mock
    private TrainingCourseDao dao;

    @InjectMocks
    private TrainingCourseService service;

    @Test
    public void callGetFeedbacksOnDao() {
        //when
        service.getFeedbacks();

        //then
        verify(dao).getFeedbacks();
    }

    @Test
    public void callGetCoursesOnDao() {
        //when
        service.getCourses();

        //then
        verify(dao).getCourses();
    }

    @Test
    public void callSaveOrUpdateTrainingCourseOnDao() {
        //given
        TrainingCourseForm form = mock(TrainingCourseForm.class);

        //when
        service.saveOrUpdateTrainingCourse(form);

        //then
        verify(dao).saveOrUpdateTrainingCourse(form);
    }

    @Test
    public void callGetSectionsOnDao() {
        //given
        TrainingCourse course = mock(TrainingCourse.class);

        //when
        service.getSections(course);

        //then
        verify(dao).getSections(course);
    }

    @Test
    public void callFindSectionOnDao() {
        //given
        Long id = 1L;

        //when
        service.findSection(id);

        //then
        verify(dao).findSection(id);
    }

    @Test
    public void callFindCourseOnDao() {
        //given
        Long id = 1L;

        //when
        service.findCourse(id);

        //then
        verify(dao).findCourse(id);
    }
}
