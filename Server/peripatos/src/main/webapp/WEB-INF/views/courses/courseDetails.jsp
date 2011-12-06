<%@include file="/WEB-INF/views/header.jsp"%>
<body>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div id="assignmentTable">
		<table class="tableClass">
			<tr>
				<th>Assignments</th>
				<th>Due Date</th>
			</tr>
			<c:forEach items="${course.assignments}" var="assignment">
				<tr>
					<td><a href="<c:url value='./${course.id}/assignments/${assignment.id}'/>">${assignment.name}</a></td>
					<td>${assignment.dueDate}</td>
					<td><a href="<c:url value='./${course.id}/assignments/${assignment.id}/answers'/>">View
							Submitted Answers</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div id="createAssignmentLink"><a href="<c:url value='./${course.id}/assignments/createAssignment'/>">Create Assignment</a></div>

	<div id="studentList">
		<div class="listTitleClass">Students</div>
		<ul>
			<c:forEach items="${students}" var="student">
				<li>${student.username}</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>