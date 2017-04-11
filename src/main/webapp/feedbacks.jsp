<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Spring 3 MVC Multipe Row Submit - viralpatel.net</title>
</head>
<body>
<h2>Show Contacts</h2>
<table width="50%">
    <tr>
        <th>Name</th>
        <th>Lastname</th>
        <th>Email</th>
        <th>Course</th>
        <th>Section</th>
        <th>Date</th>
        <th>Raiting</th>
        <th>Comment</th>
    </tr>
    <c:forEach items="${feedbacks}" var="feedback" varStatus="status">
        <tr>
            <td>${feedback.visitor.firstName}</td>
            <td>${feedback.visitor.lastName}</td>
            <td>${feedback.visitor.emailAddress}</td>
            <td>${feedback.trainingCourse.name}</td>
            <td>${feedback.favoriteSection.name}</td>
            <td>${feedback.date}</td>
            <td>${feedback.rating}</td>
            <td>${feedback.comment}</td>
        </tr>
    </c:forEach>
</table>
<br/>
<form:form action="clearStep1">
    <input type="submit" value="New feedback"/>
</form:form>

</body>
</html>