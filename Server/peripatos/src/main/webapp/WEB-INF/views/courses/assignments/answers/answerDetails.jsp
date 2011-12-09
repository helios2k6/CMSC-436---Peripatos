<%@include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div class="pageTitleClass">Answers for Assignment: ${assignment.name} for Student: ${user.username}</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div class="mainBodyAreaClass">
		<fmt:formatDate var="dueDateFormatted" pattern='yyyy-MM-dd' value='${assignment.dueDate}' />
		<div id="dueDateNotificationArea">Due Date for Assignment is: ${dueDateFormatted}</div>
		<ul class="listClass">
			<c:forEach items="${answers}" var="answer">
				<li>
					<div class="questionResponseClass">
						<div class="questionResponseQuestionTitleClass">Question: ${answer.question.title}</div>
						<div class="questionResponseBodyClass">
							<fmt:formatDate var="formattedDate" pattern='yyyy-MM-dd' value='${answer.submissionDate}' />
							Response (Submitted on: ${formattedDate}):
							<div class="responseBodyClass">${answer.body}</div>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>