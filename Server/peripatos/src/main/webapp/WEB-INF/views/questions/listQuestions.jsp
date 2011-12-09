<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div class="pageTitleClass">Question Bank</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div class="smallerMainBodyAreaClass">
		<div class="listTitleClass">Questions</div>
		<div id="createQuestionLink">
			<a href="<c:url value='./questions/createQuestion' />">Create Question</a>
		</div>
		<ul class="listClass">
			<c:forEach items="${questions}" var="question">
				<li class="listItemClass"><a href="<c:url value='./questions/${question.id}'/>">${question.title}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>