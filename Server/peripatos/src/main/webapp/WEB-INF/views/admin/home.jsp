<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div class="pageTitleClass">Administration Home</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div class="mainBodyAreaClass">
		<ul class="listClass">
			<c:forEach items="${courses}" var="course">
				<li class="listItemClass"><a href="<c:url value='./courses/${course.id}'/>">${course.name}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>

</html>