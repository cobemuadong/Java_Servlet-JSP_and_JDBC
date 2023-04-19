<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/16/2023
  Time: 6:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Courses List</title>
    <link rel="stylesheet" href="../css/table.css">
</head>
<body>
<jsp:include page="header.jsp"/>
    <c:set var="courses" value="${requestScope.courses}"/>
    <div class="table-container">
        <table style="width: 100%">
            <caption style="alignment: center"><h2>List of Courses</h2></caption>
            <tr>
                <th style="width: 10%">ID</th>
                <th style="width: 28%;">Name</th>
                <th style="width: 20%">Lecture</th>
                <th style="width: 10%">Year</th>
                <th style="width: 20%">Notes</th>
                <th style="width: 12%">Modify</th>
            </tr>
            <c:forEach var="course" items="${courses}">
                <tr class="row-content">
                    <td><c:out value="${course.id}"/></td>
                    <td><c:out value="${course.name}"/></td>
                    <td><c:out value="${course.lecture}"/></td>
                    <td><c:out value="${course.year}"/></td>
                    <td><c:out value="${course.notes}"/></td>
                    <td>
                        <div class="button-group">
                            <form method="get" action="${pageContext.request.contextPath}/edit-course">
                                <input type="hidden" name="id" value="${course.id}">
                                <input type="hidden" name="name" value="${course.name}">
                                <input type="hidden" name="lecture" value="${course.lecture}">
                                <input type="hidden" name="year" value="${course.year}">
                                <input type="hidden" name="notes" value="${course.notes}">
                                <input type="submit" class="edit-button" id="editBtn" value="Edit">
                            </form>
                            <form method="get" action="${pageContext.request.contextPath}/courses">
                                <input type="hidden" name="id" value="${course.id}">
                                <input type="hidden" name="name" value="${course.name}">
                                <input type="hidden" name="year" value="${course.year}">
                                <input type="submit" class="show-button" id="showBtn" value="Show">
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/delete-course">
                                <input type="hidden" name="id" value="${course.id}">
                                <input type="submit" class="delete-button" id="deleteBtn" name="deleteBtn" value="Delete">
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
