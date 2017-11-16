<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<table class = "table">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
			<c:forEach items = "${ currentUsers }" var = "user">
				<tr>
					<td><c:out value="${ user.firstname }" /></td>
					<td><c:out value="${ user.email}" /> </td>
					<td>
						<c:forEach items = "${ user.roles }" var = "role">
							<c:if test="${ role.getName() == 'ROLE_ADMIN' }">
								Admin
							</c:if>
							<c:if test="${ role.getName() == 'ROLE_USER' }">
								<a href = "/user/delete/${ user.id }">delete</a> &nbsp; <a href = "/user/edit/${ user.id }">make-admin</a>
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table><br>
		<form id="logoutForm" method="POST" action="/logout">
        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        		<input type="submit" value="Logout!" />
    		</form>
	</div>

</body>
</html>