<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
  <h1>Training Course Feedback Form</h1>
  <p>Please help us to improve our class by completing this form.</p>

  <form:form action="processStep2" commandName="trainingCourseForm" style="display: inline">
    <dl>
      <dt>Favorite Section:</dt>
      <dd>
        <form:select  path="favorite">
          <form:option value="" label="Please select one" />
          <c:forEach items="${sections}" var="section">
            <option <c:if test="${section.id eq trainingCourseForm.favorite.id}"> selected="true" </c:if> value="${section.id}">${section.name}</option>
          </c:forEach>
        </form:select>
      </dd>
      <form:errors path="favorite" cssClass="error"/>

      <dt>Please rate the training:</dt>
      <dd>
        <form:radiobutton path="rate" value="1"/>1
        <form:radiobutton path="rate" value="2"/>2
        <form:radiobutton path="rate" value="3"/>3
        <form:radiobutton path="rate" value="4"/>4
        <form:radiobutton path="rate" value="5"/>5
      </dd>
      <form:errors path="rate" cssClass="error"/>

      <dt>Please share with us your comments on how we can improve this class for future:</dt>
      <dd><form:textarea rows="6" cols="40" path="comment"></form:textarea></dd>
      <form:errors path="comment" cssClass="error"/>
    </dl>
    <input type="submit" value="&lt; Continue"/>
  </form:form>

  <form:form action="showStep1" commandName="trainingCourseForm" style="display: inline">
    <input type="submit" value="Back &gt;"/>
  </form:form>
</body>
</html>
