<%@include file="../header.jsp"%>
<body>
	<div id="adminHeader">Welcome: ${user.name}</div>
	<div id="questionBankLink"><a href="/questions">Question Bank</a></div>
	<div id="adminCourseArea">
		<ul>
			<c:forEach items="${courses}" var="course">
				<li><a href="<c:url value='/courses/${course.id}'></c:url>">${course.name}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>

</html>