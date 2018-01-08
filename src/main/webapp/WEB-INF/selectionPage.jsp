<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dojoscription</title>
</head>
<body>
	<div>
		<h1>Welcome to Dojoscriptions <c:out value = "${loggedInUser.getFirstname()}" /></h1>
		<h5>Please choose a subscription and start date</h5>
		
		<form:form action = "/addscription" method = "POST" modelAttribute = "selection">
			<p> <form:label path = "dueDate">Due day:</form:label>
				<form:input type = "date" path="dueDate"/>
			</p>
			<p>
				<form:label path="pack">Package: </form:label>
				<form:select path="pack">
					<c:forEach items="${packList}" var="packs">
					
						<c:if test="${ packs.status == true }">
							<form:option value="${packs.getId()}" label="${packs.name} $${ packs.cost}" />
						</c:if>
					
					</c:forEach>
				</form:select>
			</p>
			<p>
				<input type = "hidden" name = "loggedInUser" value = "${loggedInUser.getId()}" />
			</p>
			<input type="submit" value="Sign Up!"/>
		</form:form>
	</div>
</body>
</html>