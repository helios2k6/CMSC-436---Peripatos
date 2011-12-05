<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<h1>Question Bank</h1>
	<h2>Questions</h2>
	<a href="<c:url value='./questions/createQuestion' />">Create Question</a>
	<div class="unorderedList">
		<ul>
			<c:forEach items="${questions}" var="question">
				<li><a href="<c:url value='./questions/${question.id}'/>">${question.title}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>