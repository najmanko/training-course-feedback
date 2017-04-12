package com.monster.mgs.test.dao;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.model.Visitor;
import com.monster.mgs.test.web.TrainingCourseForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TrainingCourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<TrainingCourse> getCourses() {
        Criteria criteria = getSession().createCriteria(TrainingCourse.class);
        return criteria.list();
    }

    public List<TrainingCourseSection> getSections(TrainingCourse course) {
        Criteria criteria = getSession().createCriteria(TrainingCourseSection.class);
        criteria.add(Restrictions.eq("trainingCourse.id", course.getId()));
        return criteria.list();
    }

    public List<TrainingCourseFeedback> getFeedbacks() {
        Criteria criteria = getSession().createCriteria(TrainingCourseFeedback.class);
        return criteria.list();
    }

    public TrainingCourse findCourse(Long id) {
        return (TrainingCourse) getSession().get(TrainingCourse.class, id);
    }

    public TrainingCourseSection findSection(Long id) {
        return (TrainingCourseSection) getSession().get(TrainingCourseSection.class, id);
    }

    @Transactional
    public void saveOrUpdateTrainingCourse(TrainingCourseForm trainingCourseForm) {
        TrainingCourseFeedback oldFeedback = loadExistingTrainingCourseFeedback(trainingCourseForm.getEmailAddress());

        Visitor visitor = oldFeedback != null ? oldFeedback.getVisitor() : new Visitor();
        visitor.setFirstName(trainingCourseForm.getFirstName());
        visitor.setLastName(trainingCourseForm.getLastName());
        visitor.setEmailAddress(trainingCourseForm.getEmailAddress());

        TrainingCourseFeedback feedback = oldFeedback != null ? oldFeedback : new TrainingCourseFeedback();
        feedback.setVisitor(visitor);
        feedback.setTrainingCourse(trainingCourseForm.getTrainingCourse());
        feedback.setDate(trainingCourseForm.getTrainingCourseDate());
        feedback.setFavoriteSection(trainingCourseForm.getFavorite());
        feedback.setRating(trainingCourseForm.getRate());
        feedback.setComment(trainingCourseForm.getComment());

        getSession().save(feedback);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private TrainingCourseFeedback loadExistingTrainingCourseFeedback(String email) {
        Criteria c = getSession().createCriteria(TrainingCourseFeedback.class);
        c.createAlias("visitor", "visitor");
        c.add(Restrictions.eq("visitor.emailAddress", email));
        return (TrainingCourseFeedback)c.uniqueResult();
    }
}
