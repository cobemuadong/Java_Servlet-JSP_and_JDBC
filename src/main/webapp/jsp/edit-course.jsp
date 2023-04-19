<%--@elvariable id="course" type="com.example.bt3.models.Course"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.bt3.models.Student" %><%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/15/2023
  Time: 11:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Edit Course</title>
  <link rel="stylesheet" href="../css/edit-fields.css">
</head>
<body>
<jsp:include page="header.jsp"/>
  <form method="post">
    <input name="old-id" value="${course.id}" type="hidden">
    <input name="old-year" value="${course.year}" type="hidden">
    <label for="course-id">ID</label><input type="text" id="course-id" name="course-id" value="${course.id}">
    <label for="course-name">Name</label><input type="text" id="course-name" name="course-name" value="${course.name}">
    <label for="course-lecture">Lecture</label><input type="text" id="course-lecture" name="course-lecture" value="${course.lecture}">
    <label for="course-year">Year</label><input type="number" min="1900" max="2100" id="course-year" name="course-year" value="${course.year}">
    <label for="course-notes">Notes</label>
    <textarea id="course-notes" name="course-notes"><c:out value="${course.notes}"/></textarea>
    <input type="submit" name="submit">
  </form>
</body>
</html>
