<%@include file="../../header.jsp"%>

<script type="text/javascript">
	$(function(){
		//Set Date Picker for the due date thing
		$("#dueDate").datePicker({ dateFormat: 'ATOM' });
		
		//Set sortable
	});
</script>
</head>
<body>
<div class = "mainBodyAreaClass">
	<div class = "formClass">
	<form:form method="POST" modelAttribute="assignment">
		<table class="tableClass">
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
				<ul>
					<c:forEach items="${assignment.questions}" var="assignedQuestion">
						<li>${assignedQuestion.title}</li>
					</c:forEach>
				</ul>
			</div>
			<div id="availableQuestionsList">
				<ul>
					<c:forEach items="${availableQuestions}" var="availableQuestion">
						<li>${availableQuestion.title}</li>
					</c:forEach>
				</ul>
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
	</div>
</div>
</body>
</html>