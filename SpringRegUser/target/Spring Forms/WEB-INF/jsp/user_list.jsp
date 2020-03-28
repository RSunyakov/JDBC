<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 03.03.2020
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<table>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td><c:out value="${user.getEmail()}"/></td>
            <td><c:out value="${user.getPassword()}"/></td>
            <td><c:out value="${user.getGender()}"/></td>
            <td><c:out value="${user.getCountry()}"/></td>
            <td><c:out value="${user.getBirthday()}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
