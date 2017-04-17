package com.monster.mgs.test.service;

import com.monster.mgs.test.dao.TrainingCourseDao;
import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.web.TrainingCourseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TrainingCourseService {
    
    @Autowired
    private TrainingCourseDao dao;

    public List<TrainingCourseFeedback> getFeedbacks() {
        return dao.getFeedbacks();
    }

    public Object getCourses() {
        return dao.getCourses();
    }

    public void saveOrUpdateTrainingCourse(TrainingCourseForm trainingCourseForm) {
        dao.saveOrUpdateTrainingCourse(trainingCourseForm);
    }

    public List<TrainingCourseSection> getSections(TrainingCourse trainingCourse) {
        return dao.getSections(trainingCourse);
    }

    public TrainingCourseSection findSection(long id) {
        return dao.findSection(id);
    }

    public TrainingCourse findCourse(long id) {
        return dao.findCourse(id);
    }

}