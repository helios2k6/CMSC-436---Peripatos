<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<body>
	Assignment: ${assignment.name}
	<br/>
	<table>
		<c:forEach items="${students}" var="student">
			<tr><td><c:url value="${student.id}">${student.name}</c:url></td></tr>
		</c:forEach>
	</table>
</body>
</html>