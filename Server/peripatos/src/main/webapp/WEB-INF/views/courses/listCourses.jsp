<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<body>
	<table>
		<c:forEach items="${courses}" var="course">
			<tr><td><c:url value="/${course.id}">${course.name}</c:url></a></td></tr>
		</c:forEach>
	</table>
</body>
</html>