<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/18/2023
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="score" type="com.example.bt3.models.Score"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Score Board</title>
  <link rel="stylesheet" href="../css/search.css">
  <link rel="stylesheet" href="../css/table.css">
</head>
<body>
  <jsp:include page="header.jsp"/>
  <div>
    <form method="get" class="searchbar">
      <input name="id" type="hidden" value="${param.id}"/>
      <input name="pid" type="hidden" value="${param.pid}"/>
      <input name="year" placeholder="Enter year" type="number" min="1900" max="2100" value="${param.year}">
      <button type="submit" class="search-button">Search <i class="fa fa-search"></i> </button>
    </form>
  </div>
  <c:set var="scores" value="${requestScope.scores}"/>
  <c:if test="${not empty scores}">
    <div class="table-container">
      <table>
        <caption><h2>Score Board</h2></caption>
        <tr>
          <th>Course ID</th>
          <th>Course Name</th>
          <th>Year</th>
          <th>Score</th>
        </tr>
        <c:forEach var="score" items="${scores}">
          <tr>
            <td><c:out value="${score.courseId}"/></td>
            <td><c:out value="${score.courseName}"/></td>
            <td><c:out value="${score.year}"/></td>
            <td><c:out value="${score.score > 0 ? score.score : ''}"/></td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </c:if>
  
</body>
</html>
