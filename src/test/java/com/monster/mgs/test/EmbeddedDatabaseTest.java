package com.monster.mgs.test;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Test just for confirm that embedded HSQL database is working and also schema and initial data
 * are correctly placed in.
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmbeddedDatabaseTest extends AbstractTransactionalJUnit4SpringContextTests {

  @Test
  public void databaseShouldBeWorking() {
    //when
    Date timestamp = jdbcTemplate.queryForObject("VALUES(CURRENT_TIMESTAMP)", Date.class);
    
    //then
    assertNotNull(timestamp);
  }
  
  @Test
  public void schemaShouldBeCreated() {
    //when
    List<String> tableNames = jdbcTemplate.queryForList(
        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = ?",
        String.class, "PUBLIC", "BASE TABLE");
    
    //then
    assertThat(tableNames, hasItems("VISITOR", "TRAINING_COURSE", "TRAINING_COURSE_SECTION", "TRAINING_COURSE_FEEDBACK"));
  }
  
  @Test
  public void initDataShouldBeInsertedIntoCourse() {
    checkCountInTable("TRAINING_COURSE", 2);
  }
  
  @Test
  public void initDataShouldBeInsertedIntoCourseSection() {
    checkCountInTable("TRAINING_COURSE_SECTION", 20);
  }
  
  private void checkCountInTable(String tableName, long countAtLeast) {
    Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Long.class);
    assertThat(count, greaterThanOrEqualTo(countAtLeast));
  }
}
