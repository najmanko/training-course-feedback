package com.monster.mgs.test.util;

import com.monster.mgs.test.model.TrainingCourse;
import com.monster.mgs.test.model.TrainingCourseFeedback;
import com.monster.mgs.test.model.TrainingCourseSection;
import com.monster.mgs.test.model.Visitor;
import com.monster.mgs.test.web.TrainingCourseForm;

import java.util.Date;

public class DataGenerator {
    
    public static TrainingCourseForm generateTrainingCourseForm() {
        TrainingCourseForm form = new TrainingCourseForm();
        form.setFirstName("Chuck");
        form.setLastName("Norris");
        form.setEmailAddress("chuck@norris.com");
        form.setComment("Best fighter");
        form.setRate((byte)1);
        form.setTrainingCourseDate(new Date(0L));

        TrainingCourse course = new TrainingCourse();
        course.setId(1L);
        course.setName("Course1");
        form.setTrainingCourse(course);

        TrainingCourseSection section = new TrainingCourseSection();
        section.setId(1L);
        section.setName("Section1");
        form.setFavorite(section);

        return form;
    }
    
    public static TrainingCourseFeedback generateFeedback() {
        TrainingCourseFeedback feedback = new TrainingCourseFeedback();
        feedback.setId(1L);

        Visitor visitor = new Visitor();
        visitor.setFirstName("oldFirstName");
        visitor.setLastName("oldLastName");
        visitor.setEmailAddress("old@email.com");
        feedback.setVisitor(visitor);

        feedback.setComment("Old comment");
        feedback.setRating((byte)9);
        feedback.setDate(new Date(0L));

        TrainingCourse oldCourse = new TrainingCourse();
        oldCourse.setId(9L);
        oldCourse.setName("Old courrse");
        feedback.setTrainingCourse(oldCourse);

        TrainingCourseSection oldSection = new TrainingCourseSection();
        oldSection.setId(9L);
        oldSection.setName("Old section");
        feedback.setFavoriteSection(oldSection);

        return feedback;
    }
}
