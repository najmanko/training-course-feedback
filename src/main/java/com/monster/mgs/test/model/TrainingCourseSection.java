package com.monster.mgs.test.model;

import javax.persistence.*;

@Entity
@Table(name = "TRAINING_COURSE_SECTION")
public class TrainingCourseSection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRAINING_COURSE_SECTION_ID", nullable = false)
  Long id;

  @Column(nullable = false)
  String name;

  @ManyToOne
  @JoinColumn(name="TRAINING_COURSE_ID", nullable = false)
  private TrainingCourse trainingCourse;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TrainingCourse getTrainingCourse() {
    return trainingCourse;
  }

  public void setTrainingCourse(TrainingCourse trainingCourse) {
    this.trainingCourse = trainingCourse;
  }
}
