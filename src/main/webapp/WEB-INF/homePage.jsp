<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome Page <c:out value="${currentUser.firstname}"></c:out></h1>
    <fieldset>
    	<p>First Name: <c:out value="${ currentUser.firstname }"></c:out></p>
    	<p>Last Name: <c:out value="${ currentUser.lastname }"></c:out></p>
    	<p>Email: <c:out value="${ currentUser.email }"></c:out></p>
    	<p>Sign up date: <fmt:formatDate type = "date" value="${ currentUser.createdAt }" pattern = "MM/dd/yyyy" /></p>
    	<p>Last Sign in: <fmt:formatDate type = "date" value="${ currentUser.createdAt }" pattern = "MM/dd/yyyy" /></p>
    </fieldset><br>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
</body>
</html>