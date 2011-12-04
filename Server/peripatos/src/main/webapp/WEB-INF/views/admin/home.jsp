<%@include file="../header.jsp"%>
</head>
<body>
	<div class="pageTitle">Administration Home</div>
	<div id="adminHeader">Welcome: ${user.username}</div>
	<!--
	<a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Logout</a>
	-->
	<div id="questionBankLink"><a href="<c:url value='/questions'/>">Question Bank</a></div>
	<div id="adminCourseArea">
		<ul>
			<c:forEach items="${courses}" var="course">
				<li><a href="<c:url value='/courses/${course.id}'></c:url>">${course.name}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>

</html>