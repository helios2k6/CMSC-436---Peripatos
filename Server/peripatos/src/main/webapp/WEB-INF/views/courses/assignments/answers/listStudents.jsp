<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div class="pageTitleClass">Student Selection</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div class="mainBodyAreaClass">
		<div class="pageSubtitleClass">Choose Which Student's Answers to View for ${assignment.name}</div>
		<div class="listTitleClass">Students</div>
		<div id="listArea">
			<ul class="listClass">
				<c:forEach items="${students}" var="student">
					<li class="listItemClass"><a href="<c:url value='./answers/${student.username}'/>">${student.username}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>