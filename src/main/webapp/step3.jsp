<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
  <h1>Training Course Feedback Form</h1>
  <p>Please help us to improve our class by completing this form.</p>

  <h2>Summary page</h2>
  <dl>
    <dt>First Name: ${trainingCourseForm.firstName}</dt>
    <dd></dd>

    <dt>Last Name: ${trainingCourseForm.lastName}</dt>
    <dd></dd>

    <dt>Email Address: ${trainingCourseForm.emailAddress}</dt>
    <dd></dd>

    <dt>Training Course: ${trainingCourseForm.trainingCourse.name}</dt>
    <dd></dd>

    <dt>Training Course Date: ${trainingCourseForm.trainingCourseDate}</dt>
    <dd></dd>

    <dt>Favorite Section: ${trainingCourseForm.favorite.name}</dt>
    <dd></dd>

    <dt>Rating: ${trainingCourseForm.rate}</dt>
    <dd></dd>

    <dt>Comments: ${trainingCourseForm.comment}</dt>
    <dd></dd>
  </dl>

  <form action="processStep1" style="display: inline">
    <input type="submit" value="&lt; Back"/>
  </form>

  <form:form action="processStep3" style="display: inline">
    <input type="submit" value="Send Feedback" style="font-weight: bold"/>
  </form:form>
</body>
</html>
