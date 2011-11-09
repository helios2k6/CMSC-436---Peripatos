<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title></title>
</head>	
<body>
	<form:form modelAttribute="user" method="POST">
		Name: <form:input path="name" /><br/>
		Passsword: <form:input path="password" /><br/>
		<input type="submit"/>
	</form:form>
</body>
</html>