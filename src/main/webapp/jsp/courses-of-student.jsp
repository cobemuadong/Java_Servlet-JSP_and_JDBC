<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/12/2023
  Time: 10:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Student's Courses</title>
    <link rel="stylesheet" href="../css/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}../css/search.css">
</head>
<body>
<jsp:include page="header.jsp"/>
    <div class="searchbar">
        <form method="get">
            <input name="id" type="hidden" value="${param.id}"/>
            <input name="pid" type="hidden" value="${param.pid}"/>
            <input name="year" type="number" min="1900" max="2100" value="${param.year}">
            <button type="submit" class="search-button">Search <i class="fa fa-search"></i> </button>
        </form>
    </div>
<c:set var="courses" value="${requestScope.courses}"/>
<%--@elvariable id="course" type="com.example.bt3.models.Course"--%>
<c:if test="${not empty courses}">
    <c:set var="courses" value="${requestScope.courses}"/>
    <div class="table-container">
        <table style="width: 100%">
            <caption style="alignment: center"><h2>Courses of student ${param["id"]} in year ${param["year"]}</h2></caption>
            <tr>
                <th style="width: 10%">Course ID</th>
                <th style="width: 30%;">Course Name</th>
                <th style="width: 25%">Lecture</th>
                <th style="width: 10%">Year</th>
                <th style="width: 25%">Notes</th>
            </tr>
            <c:forEach var="course" items="${courses}">
                <tr class="row-content">
                    <td><c:out value="${course.id}"/></td>
                    <td><c:out value="${course.name}"/></td>
                    <td><c:out value="${course.lecture}"/></td>
                    <td><c:out value="${course.year}"/></td>
                    <td><c:out value="${course.notes}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
<c:if test="${empty courses}">
    <p style="text-align: center; color: #666666; font-style: italic">Empty</p>
</c:if>
</body>
</html>
