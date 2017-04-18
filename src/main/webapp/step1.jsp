<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
  <h1>Training Course Feedback Form</h1>
  <p>Please help us to improve our class by completing this form.</p>

  <form:form action="processStep1" commandName="trainingCourseForm" style="display: inline">
    <dl>
      <dt>First Name:</dt>
      <dd><form:input type="text" path="firstName"/></dd>
      <form:errors path="firstName" cssClass="error"/>


      <dt>Last Name:</dt>
      <dd><form:input type="text" path="lastName"/></dd>
      <form:errors path="lastName" cssClass="error"/>

      <dt>Email Address:</dt>
      <dd><form:input type="text" path="emailAddress"/></dd>
      <form:errors path="emailAddress" cssClass="error"/>

      <dt>Training Course:</dt>
      <dd>
        <form:select path="trainingCourse">
          <form:option value="" label="Please select one" />
          <c:forEach items="${listOfTrainingCourses}" var="course">
            <option <c:if test="${course.id eq trainingCourseForm.trainingCourse.id}"> selected="true" </c:if> value="${course.id}">${course.name}</option>
          </c:forEach>
        </form:select>
      </dd>
      <form:errors path="trainingCourse" cssClass="error"/>

      <dt>Training Course Date:</dt>
      <dd>
        <form:input type="date" path="trainingCourseDate"/>
      </dd>
      <form:errors path="trainingCourseDate" cssClass="error"/>
    </dl>

    <input type="submit" value="&lt; Continue" />
  </form:form>

  <form action="clearStep1" style="display: inline">
    <input type="submit" value="Cancel &gt;" />
  </form>
</body>
</html>