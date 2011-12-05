<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div id="title">Choose Which Student's Answers To View</div>
	<div id="listStudentArea">
		<div id="assignmentTitle">${assignment.name}</div>
		<div id="listTitle">Students</div>
		<div id="listArea">
		<ul>
		<c:forEach items="${students}" var="student">
			<li><a href="<c:url value='./${student.username}'/>">${student.username}</a></li>
		</c:forEach>
		</ul>
		</div>
	</div>
</body>
</html>