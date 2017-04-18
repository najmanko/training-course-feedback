package com.monster.mgs.test.dao;

import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.web.TrainingCourseForm;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.monster.mgs.test.util.DataGenerator.generateFeedback;
import static com.monster.mgs.test.util.DataGenerator.generateTrainingCourseForm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrainingCourseDaoTest {

    @Mock (answer = RETURNS_DEEP_STUBS)
    private SessionFactory sessionFactory;
    
    @InjectMocks
    private TrainingCourseDao courseDao;

    @Captor
    private ArgumentCaptor<TrainingCourseFeedback> feedbackArgumentCaptor;
    
    @Before
    public void setup() {
        given(sessionFactory.getCurrentSession().save(any(TrainingCourseFeedback.class))).willReturn(1L);
    }

    @Test
    public void saveNewTrainingCourseFeedback() {
        //given
        TrainingCourseForm form = generateTrainingCourseForm();
        given(sessionFactory.getCurrentSession().createCriteria(TrainingCourseFeedback.class)).willReturn(mock(Criteria.class));

        //when
        courseDao.saveOrUpdateTrainingCourse(form);

        //then
        verify(sessionFactory.getCurrentSession()).save(feedbackArgumentCaptor.capture());
        TrainingCourseFeedback tcf = feedbackArgumentCaptor.getValue();
        isEqual(tcf, form);
        assertNull(tcf.getId());
    }

    @Test
    public void updateOldTrainingCourseFeedback() {
        //given
        TrainingCourseForm form = generateTrainingCourseForm();
        Criteria criteria = mock(Criteria.class);
        TrainingCourseFeedback feedback = generateFeedback();
        given(criteria.uniqueResult()).willReturn(feedback);
        given(sessionFactory.getCurrentSession().createCriteria(TrainingCourseFeedback.class)).willReturn(criteria);

        //when
        courseDao.saveOrUpdateTrainingCourse(form);

        //then
        verify(sessionFactory.getCurrentSession()).save(feedbackArgumentCaptor.capture());
        TrainingCourseFeedback tcf = feedbackArgumentCaptor.getValue();
        isEqual(tcf, form);
        assertEquals(tcf.getId(), feedback.getId());
    }

    private void isEqual(TrainingCourseFeedback tcf, TrainingCourseForm form) {
        assertEquals(tcf.getVisitor().getFirstName(), form.getFirstName());
        assertEquals(tcf.getVisitor().getLastName(), form.getLastName());
        assertEquals(tcf.getVisitor().getEmailAddress(), form.getEmailAddress());
        assertEquals(tcf.getComment(), form.getComment());
        assertEquals(tcf.getRating(), form.getRate());
        assertEquals(tcf.getDate(), form.getTrainingCourseDate());
        assertEquals(tcf.getTrainingCourse(), form.getTrainingCourse());
        assertEquals(tcf.getFavoriteSection(), form.getFavorite());
    }
}
