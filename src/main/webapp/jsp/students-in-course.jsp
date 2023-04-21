<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/17/2023
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <c:set var="name" value="${requestScope.courseName}"/>
    <title>Student in <c:out value="${name}"/> </title>
    <link rel="stylesheet" href="../css/table.css">
    <link rel="stylesheet" href="../css/floating.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<%--    <sql:setDataSource var="myDB" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/bt3" user="root" password=""/>--%>
<%--    <sql:query var="listStudents" dataSource="${myDB}">--%>
<%--        SELECT * from students--%>
<%--    </sql:query>--%>
<%--@elvariable id="students" type="com.example.bt3.models.Student"--%>
<c:set var="students" value="${students}"/>
<div class="table-container">
    <table style="width: 100%" vc>
        <caption style="alignment: center"><h2>Students in ${name}</h2></caption>
        <tr>
            <th style="width: 10%">ID</th>
            <th style="width: 20%;">Name</th>
            <th style="width: 5%">Grade</th>
            <th style="width: 10%">Birthday</th>
            <th style="width: 30%">Address</th>
            <th style="width: 20%">Notes</th>
            <th style="width: 5%">Modify</th>
        </tr>
        <c:forEach var="student" items="${students}">
            <tr class="row-content">
                <td><c:out value="${student.id}"/></td>
                <td><c:out value="${student.name}"/></td>
                <td><c:out value="${student.grade}"/></td>
                <fmt:formatDate value="${student.birthday}" pattern="dd-MM-yyyy" var="birthday"/>
                <td style="text-align: center"><c:out value="${birthday}"/></td>
                <td><c:out value="${student.address}"/></td>
                <td><c:out value="${student.notes}"/></td>
                <td>
                    <div class="button-group">
                        <form method="post"
                              action="${pageContext.request.contextPath}/courses/delete-student-from-course">
                            <input type="hidden" name="course-name" value="${requestScope.courseName}">
                            <input type="hidden" name="course-id" value="${requestScope.courseId}">
                            <input type="hidden" name="student-id" value="${student.id}">
                            <input type="hidden" name="year" value="${requestScope.courseYear}">
                            <input type="submit" class="delete-button" id="deleteBtn" value="Delete">
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<button class="floating-button" onclick="openFloatingForm()">
    <span>+</span>
</button>
<div class="floating-form-container" id="floatingFormContainer">
    <button class="close-button" onclick="closeFloatingForm()">&times;</button>
    <form method="post" action="${pageContext.request.contextPath}/courses/insert-into-course">
        <!-- Your form content goes here -->
        <h2>Add Student To ${name}</h2>
        <label for="student-id" style="width: 10%">ID:</label>
        <input name="queryString" type="hidden" value="${pageContext.request.queryString}">
        <input name="course-id" value="${requestScope.courseId}" type="hidden">
        <input name="year" value="${requestScope.courseYear}" type="hidden">
        <input style="width: 68.9%" type="number" id="student-id" name="student-id">
        <label for="student-score">Score:</label><input style="width: 68.9%" type="number" step="0.01" id="student-score" name="student-score">
        <input type="submit" style="width: 20%" value="Submit">
    </form>
</div>
    <script>
        function openFloatingForm() {
            // Show the floating form container
            document.getElementById("floatingFormContainer").style.display = "block";
        }
        
        function closeFloatingForm() {
            // Hide the floating form container
            document.getElementById("floatingFormContainer").style.display = "none";
        }
    </script>
</body>
</html>
