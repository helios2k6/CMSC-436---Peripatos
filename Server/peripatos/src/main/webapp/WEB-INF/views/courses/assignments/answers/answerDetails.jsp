<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div id="titleArea">Answer for Assignment: ${assignment.name}</div>
	<div id="bodyArea">
		<c:forEach items="${answers}" var="answer">
			<div class="questionTitleForAnswer">Question:
				${answer.question.title}</div>
			<div class="responseTitle">
				Response:
				<div class="responseBody">${answer.body}</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>