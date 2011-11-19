<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<body>
	${course.name}
	<br/>
	Assignments
	<table>
		<c:forEach items="${course.assignments}" item="course_assignment">
			<tr><td><c:url value="/assignments/${course_assignment.id}">${course_assignment.name}</c:url></td></tr>
		</c:forEach>
	</table>
</body>
</html>