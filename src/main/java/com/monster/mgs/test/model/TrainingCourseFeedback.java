package com.monster.mgs.test.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "TRAINING_COURSE_FEEDBACK")
public class TrainingCourseFeedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRAINING_COURSE_FEEDBACK_ID", nullable = false)
  Long id;

  @Column(name = "TRAINING_COURSE_DATE", nullable = false)
  Date date;

  @Column(nullable = false)
  private Byte rating;

  @Lob
  @Column(nullable = false)
  String comment;

  @ManyToOne(fetch = EAGER, cascade = ALL)
  @JoinColumn(name="VISITOR_ID", nullable = false)
  private Visitor visitor;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "TRAINING_COURSE_ID", nullable = false)
  private TrainingCourse trainingCourse;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "FAVORITE_SECTION_ID", nullable = false)
  private TrainingCourseSection favoriteSection;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Byte getRating() {
    return rating;
  }

  public void setRating(Byte rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Visitor getVisitor() {
    return visitor;
  }

  public void setVisitor(Visitor visitor) {
    this.visitor = visitor;
  }

  public TrainingCourse getTrainingCourse() {
    return trainingCourse;
  }

  public void setTrainingCourse(TrainingCourse trainingCourse) {
    this.trainingCourse = trainingCourse;
  }

  public TrainingCourseSection getFavoriteSection() {
    return favoriteSection;
  }

  public void setFavoriteSection(TrainingCourseSection favoriteSection) {
    this.favoriteSection = favoriteSection;
  }
}
