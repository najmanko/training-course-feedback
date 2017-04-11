package com.monster.mgs.test.model;

import javax.persistence.*;

@Entity
@Table(name = "TRAINING_COURSE")
public class TrainingCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRAINING_COURSE_ID", nullable = false)
  Long id;
  
  @Column(nullable = false)
  String name;

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
}
