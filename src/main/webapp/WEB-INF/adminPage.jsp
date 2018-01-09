<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Dashboard</title>
</head>
<body>
		<form id="logoutForm" method="POST" action="/logout">
        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        		<input type="submit" value="Logout!" />
    		</form>
	<h1>Admin Dashboard</h1>
	<div>
		<h2>Custormers</h2>
		<table>
			<tr>
				<th>Name</th>
				<th>Next Due Date</th>
				<th>Amount Due</th>
				<th>Package Type</th>
			</tr>
			<c:forEach items = "${ currentUsers }" var = "user">
			<tr>
					<c:forEach items = "${ user.roles }" var = "role">
						<c:if test="${ role.getName() == 'ROLE_USER' }">
							<td><c:out value="${ user.firstname }" /></td>
							<td><c:out value="${ user.dueDate }" /></td>
							<td><c:out value="${ user.getPack().cost }" /></td>
							<td><c:out value="${ user.getPack().getName() }" /></td>
						</c:if>
					</c:forEach>
				
			</tr>
			</c:forEach>
		</table>
	</div>
	<div>
		<h2>Packages</h2>
		<table>
			<tr>
				<th>Package Name</th>
				<th>Package Cost</th>
				<th>Avilable</th>
				<th>Users</th>
				<th>Actions</th>
			</tr>
			<c:forEach items = "${ allpackages }" var = "pack">
			<tr>
				<td><c:out value="${ pack.name }" /></td>
				<td>$<c:out value="${ pack.cost }" /></td>
				
				<td>
					<c:if test="${ pack.status == true }">
						Available
					</c:if>
					<c:if test="${ pack.status == false }">
						Unavilable
					</c:if>
				
				<td><c:out value="${ pack.getNumUsers() }" /></td>
				
				<td>
					<c:if test="${ pack.status == true }">
						<a href = "/admin/deactivate/${ pack.id }">deactivate</a>
						<c:if test = "${ pack.getNumUsers() == 0 }" >
							<a href = "/pack/delete/${ pack.id }">delete</a> 
						</c:if>
					</c:if>
					<c:if test="${ pack.status == false }">
						<a href = "/admin/activate/${ pack.id }">activate</a>
						<c:if test = "${ pack.getNumUsers() == 0 }" >
							<a href = "/pack/delete/${ pack.id }">delete</a> 
						</c:if>
					</c:if>
				</td>
			</tr>
			</c:forEach>
			<tr></tr>
		</table>
	</div>
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
		
	</div>
	
	<div>	
		<form:form action = "/admin/newPackage" method = "POST" modelAttribute = "pack">
			<fieldset>
				<legend>Create Package</legend>
				<p>
					<form:label path = "name">Package Name: </form:label> 
					<form:input path = "name" />
				</p>
				<p>
					<form:label path = "cost">Cost: </form:label> 
					 <form:input type = "number" min = "0" path = "cost" />
				</p>
				<p>
					<input type = "submit" value = "Create new Package" />
			</fieldset>
		</form:form>
	</div>

</body>
</html>