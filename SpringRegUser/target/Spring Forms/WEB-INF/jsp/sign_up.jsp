<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 28.02.2020
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<c:if test="${not empty message}">
    <h1>${message}</h1>
    <br>
</c:if>
<c:if test ="${not empty emailmessage}">
      <h1>${emailmessage}</h1>
      <br>
</c:if>
    <form:form  modelAttribute="signUpDto" method="post">
    <form:label path="email">Email</form:label><br>
    <form:input path="email"/>
    <form:errors path="email"/><br>
    <form:label path="password">Password</form:label><br>
    <form:input path="password"/>
    <form:errors path="password"/><br>
   <%-- <form:label path ="repeatPassword">Repeat Password</form:label>
    <form:input path="repeatPassword"></form:input>--%>
    <form:label path="gender">Gender</form:label><br>
    <form:input path="gender"/>
    <form:errors path="gender"/><br>
    <form:label path="country">Choose Country</form:label><br>
    <form:input path="country"/>
    <form:errors path="country"/><br>
    <form:label path="birthday"> Select your birthday</form:label><br>
    <form:input type="date" path="birthday"/>
    <form:errors path="birthday"/><br>
    <%--добавить дату рождение и согласие--%>
    <input type="submit" value="Submit" />
    </form:form>
</body>
</html>
