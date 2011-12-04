<%@include file="../../header.jsp"%>

<script type="text/javascript">
	
</script>
</head>
<body>
	<form:form method="POST" modelAttribute="assignment">
		<table>
			<tr>
				<td>Name:</td>
				<td><form:input path="name" value="${assignment.name}" /></td>
			</tr>
			<tr>
				<td>Date:</td>
				<td><form:input path="dueDate" readonly="readonly"
						value="${assignment.dueDate}" /></td>
			</tr>
		</table>
		<div id="questionPickerHeader">
			<div id="questionTitle">Assigned Questions</div>
			<div id="questionBankTitle">Available Questions</div>
			<div id="assignedQuestionsList">
				<ol>
					<c:forEach items="${assignment.questions}" var="assignedQuestion">
						<li>${assignedQuestion.title}</li>
					</c:forEach>
				</ol>
			</div>
			<div id="availableQuestionsList">
				<ol>
					<c:forEach items="${availableQuestions}" var="availableQuestion">
						<li>${availableQuestion.title}</li>
					</c:forEach>
				</ol>
			</div>
		</div>
		<div id="formFooter">
			<input type="submit" name="submit" value="Save" /> <input
				type="button" name="cancel" value="Cancel" />
		</div>
		<form:hidden path="questions" />
	</form:form>
	<form:form method="DELETE">
		<input type="submit" value="Delete" />
	</form:form>
</body>
</html>