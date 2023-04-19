<%--
  Created by IntelliJ IDEA.
  User: Huy Truong
  Date: 4/14/2023
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Oops Error!!</title>
</head>
<body>
  <h3>Sorry an exception occured!</h3>

  Exception is: ${requestScope.errorMessage}%>
</body>
</html>
