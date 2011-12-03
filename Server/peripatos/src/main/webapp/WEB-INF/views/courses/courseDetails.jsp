<%@include file="../header.jsp"%>
<body>
	<div id="assignmentTable">
		<table>
			<tr>
				<th>Assignments</th>
				<th>Due Date</th>
			</tr>
			<c:forEach items="${course.assignments}" var="assignment">
				<tr>
					<td><a href="<c:url value='assignments/${assignment.id}'/>">${assignment.name}</a></td>
					<td>${assignment.date}</td>
					<td><a href="<c:url value='assignments/${assignment.id}/answers'/>">View Submitted Answers</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div id="submittedAnswersLink">View Submitted Answers</div>

	<div id="studentList">
		<div id="studentListTitle">Students</div>
		<ul>
			<c:forEach items="${students}" var="student">
				<li>${student.username}</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>