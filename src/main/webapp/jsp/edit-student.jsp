<%--@elvariable id="student" type="com.example.bt3.models.Student"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.bt3.models.Student" %><%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/15/2023
  Time: 11:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-fields.css">
</head>
<body>
  <jsp:include page="header.jsp"/>

  <form method="post">
      <input name="old-id" value="${student.id}" type="hidden">
      <label for="student-id">ID</label><input type="text" id="student-id" name="student-id" value="${student.id}">
      <label for="student-name">Name</label><input type="text" id="student-name" name="student-name" value="${student.name}">
      <label for="student-grade">Grade</label><input type="text" id="student-grade" name="student-grade" value="${student.grade}">
<%--      <fmt:parseDate pattern=--%>
      <fmt:formatDate value="${student.birthday}" pattern="yyyy-MM-dd" var="birthday"/>
      <label for="student-birthday">Birthday</label>
      <input
          type="date"
          id="student-birthday" name="student-birthday" value="${birthday}" placeholder="DD/MM/YYYY">
      <label for="student-address">Address</label><input type="text"  id="student-address" name="student-address" value="${student.address}">
      <label for="student-notes">Notes</label>
      <textarea id="student-notes" name="student-notes"><c:out value="${student.notes}"/></textarea>
      <input type="submit" name="submit">
  </form>
</body>
</html>
