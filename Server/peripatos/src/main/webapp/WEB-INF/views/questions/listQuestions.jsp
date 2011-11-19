<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	</head>
	<body>
		<table>
		<c:forEach items="${questions}" var="question">
			<tr>
				<td>${question.title></td>
				<td>${question.body}</td>
				<td>${question.location.latitude}</td>
				<td>${question.location.longitude}</td>
			</tr>
		</c:forEach>
	</body>
</html>