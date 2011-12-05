<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/views/banner.jsp" %>
	<div class="pageTitle">Administration Home</div>
	<div id="adminHeader">Welcome: ${user.username}</div>
	<div id="questionBankLink"><a href="<c:url value='./questions'/>">Question Bank</a></div>
	<div id="adminCourseArea">
		<ul>
			<c:forEach items="${courses}" var="course">
				<li><a href="<c:url value='./courses/${course.id}'/>">${course.name}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>

</html>