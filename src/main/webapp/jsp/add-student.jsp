<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/13/2023
  Time: 12:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Add Student</title>
  <link rel="stylesheet" href="../css/edit-fields.css">
</head>
<body>
  <jsp:include page="header.jsp"/>
  <div class="container" >
    <form method="post" id="col-1">
      <label for="student-id">ID</label><input type="text" id="student-id" name="student-id">
      <label for="student-name">Name</label><input type="text" id="student-name" name="student-name">
      <label for="student-grade">Grade</label><input type="text" id="student-grade" name="student-grade">
      <label for="student-birthday">Birthday</label><input type="date" id="student-birthday" name="student-birthday">
      <label for="student-address">Address</label><input type="text"  id="student-address" name="student-address">
      <label for="student-notes">Notes</label><textarea id="student-notes" name="student-notes"></textarea>
      <input type="submit" name="submit">
    </form>
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
