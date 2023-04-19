<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/15/2023
  Time: 10:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="../css/edit-fields.css">
    <title>Add Course</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="form" id="col-1">
        <form method="post">
            <label for="class-id">ID</label><input type="text" id="class-id" name="course-id">
            <label for="course-name">Name</label><input type="text" id="course-name" name="course-name">
            <label for="lecture">Lecture</label><input type="text" id="lecture" name="course-lecture">
            <label for="year">Year</label><input type="number" min="1900" max="2100" id="year" name="course-year">
            <label for="notes">Notes</label><input type="text"  id="notes" name="course-notes">
            <input type="submit" name="submit">
        </form>
    </div>
</div>

<%--  <ul>--%>
<%--    <li><p><b>First Name:</b>--%>
<%--      <%= request.getParameter("student_name")%>--%>
<%--      ${param.student_name}--%>
<%--    </p></li>--%>
<%--    <li><p><b>Last  Name:</b>--%>
<%--      <%= request.getParameter("student_grade")%>--%>
<%--    </p></li>--%>
<%--  </ul>--%>
</body>
</html>
