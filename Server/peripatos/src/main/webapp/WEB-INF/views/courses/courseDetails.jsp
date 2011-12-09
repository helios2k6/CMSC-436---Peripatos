<%@include file="/WEB-INF/views/header.jsp"%>
<body>
	<div class="pageTitleClass">Course Details for ${course.name}</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	
	<div class="mediumMainBodyAreaClass">
		<div id="createAssignmentLink">
			<a href="<c:url value='./${course.id}/assignments/createAssignment'/>">Create Assignment</a>
		</div>
		
		<div id="assignmentTable">
			<div class="listTitleClass">Assignments</div>
			<table class="tableClass">
				<tr>
					<th>Assignment Name</th>
					<th>Due Date</th>
				</tr>
				<c:forEach items="${course.assignments}" var="assignment">
					<tr>
						<td><a href="<c:url value='./${course.id}/assignments/${assignment.id}'/>">${assignment.name}</a></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${assignment.dueDate}"/></td>
						<td><a href="<c:url value='./${course.id}/assignments/${assignment.id}/answers'/>">View Submitted Answers</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div id="studentList">
			<div class="listTitleClass">Students</div>
			<ul class="listClass">
				<c:forEach items="${students}" var="student">
					<li>${student.username}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>