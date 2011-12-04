<%@include file="header.jsp" %>
</head>
<body>
	<form:form modelAttribute="user" method="POST">
		Name: <form:input path="name" />
		<br />
		Password: <form:input path="password" />
		<br />
		<input type="submit" />
	</form:form>
</body>
</html>