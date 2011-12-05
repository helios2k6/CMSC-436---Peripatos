<%@include file="/WEB-INF/views/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>
<body>
	<c:url var="destination" value="/j_spring_security_check" />
	<form:form method="POST" action="${destination}">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="j_username" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td>Remember Me</td>
				<td><input type="checkbox" name="_spring_security_remember_me" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login" /></td>
				<td><input type="reset" value="Reset" /></td>
			</tr>
		</table>

	</form:form>
</body>
</html>