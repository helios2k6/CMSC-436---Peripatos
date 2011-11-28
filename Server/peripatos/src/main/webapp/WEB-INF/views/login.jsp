<%@include file="header.jsp" %>
<body>
	<form:form modelAttribute="user" method="POST">
		Name: <form:input path="name" />
		<br />
		Passsword: <form:input path="password" />
		<br />
		<input type="submit" />
	</form:form>
</body>
</html>