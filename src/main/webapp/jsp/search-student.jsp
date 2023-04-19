<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/15/2023
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Search Student</title>
    <link rel="stylesheet" href="../css/search.css">
    <link rel="stylesheet" href="../css/table.css"/>
</head>
<body>
  <jsp:include page="header.jsp"/>
  <form method="get" class="searchbar" action="${pageContext.request.contextPath}/search-student">
      <input type="text" name="search" id="search-input" placeholder="Enter name of student..." class="input-field"/>
      <button type="submit" class="search-button">Search <i class="fa fa-search"></i> </button>
  </form>
  <c:if test="${not empty students}">
      <div class="table-container">
          <table>
              <caption style="alignment: center"><h2>List of Students</h2></caption>
              <tr>
                  <th style="width: 10%">ID</th>
                  <th style="width: 20%;">Name</th>
                  <th style="width: 5%">Grade</th>
                  <th style="width: 15%">Birthday</th>
                  <th style="width: 25%">Address</th>
                  <th style="width: 15%">Notes</th>
                  <th style="width: 10%">Modify</th>
              </tr>
              <c:forEach var="student" items="${students}">
                  <tr class="row-content">
                      <td><c:out value="${student.id}"/></td>
                      <td><c:out value="${student.name}"/></td>
                      <td><c:out value="${student.grade}"/></td>
                      <fmt:formatDate value="${student.birthday}" pattern="dd-MM-yyyy" var="birthday" />
                      <td style="text-align: center"><c:out value="${birthday}"/></td>
                      <td><c:out value="${student.address}"/></td>
                      <td><c:out value="${student.notes}"/></td>
                      <td>
                          <div class="button-group">
                              <form method="get" action="${pageContext.request.contextPath}/edit-student">
                                  <input type="hidden" name="id" value="${student.id}"/>
                                  <input type="hidden" name="name" value="${student.name}"/>
                                  <input type="hidden" name="grade" value="${student.grade}"/>
                                  <input type="hidden" name="birthday" value="${student.birthday}"/>
                                  <input type="hidden" name="address" value="${student.address}"/>
                                  <input type="hidden" name="notes" value="${student.notes}"/>
                                  <input type="submit" class="edit-button" id="editBtn" value="Edit">
                              </form>
                                  <%--                    form for delete a course--%>
                              <form method="post" action="${pageContext.request.contextPath}/delete-student"><%--                        <input type="hidden" name="id" value="${student.id}">--%>
                                  <input type="hidden" name="id" value="${student.id}">
                                  <input type="submit" class="delete-button" id="deleteBtn" value="Delete">
                              </form>
                          </div>
                      </td>
                  </tr>
              </c:forEach>
          </table>
      </div>
  </c:if>
</body>
</html>
