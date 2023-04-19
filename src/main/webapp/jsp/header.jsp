<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/13/2023
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href=../css/header.css rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="navbar">
    <div class="dropdown">
        <button class="drop-button">Manage Student List
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/add-student">Add</a>
            <a href="${pageContext.request.contextPath}/students">Student List</a>
            <div class="popup-menu">
                <button class="popup-button">Sort By Name  <i class="fa fa-caret-right"></i></button>
                <div class="popup-content">
                    <a href="${pageContext.request.contextPath}/students?sorted=a-z">A to Z</a>
                    <a href="${pageContext.request.contextPath}/students?sorted=z-a">Z to A</a>
                </div>
            </div>
            <div class="popup-menu">
                <button class="popup-button">Sort By Grade  <i class="fa fa-caret-right"></i></button>
                <div class="popup-content">
                    <a href="${pageContext.request.contextPath}/students?sorted=increasing-grade">Increasing Grade</a>
                    <a href="${pageContext.request.contextPath}/students?sorted=decreasing-grade">Decreasing Grade</a>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/search-student">Find By Name</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="drop-button">Manage Course List
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/add-course">Add</a>
<%--            <a href="#">Edit</a>--%>
<%--            <a href="#">Delete</a>--%>
            <a href="${pageContext.request.contextPath}/courses">Courses List</a>
            <div class="popup-menu">
                <button class="popup-button">Sort By Name<i class="fa fa-caret-right"></i></button>
                <div class="popup-content">
                    <a href="${pageContext.request.contextPath}/courses?sorted=a-z">A to Z</a>
                    <a href="${pageContext.request.contextPath}/courses?sorted=z-a">Z to A</a>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/search-courses">Find By Name</a>
<%--            <a href="#">Show The Students In Course</a>--%>
        </div>
    </div>
</div>
</body>
</html>
