<%@include file="../../header.jsp"%>
<body>
	<form:form method="POST">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" value="${assignment.name}" /></td>
			</tr>
			<tr>
				<td>Date:</td>
				<td><input type="text" name="dueDate" readonly="readonly"
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
			<input type="button" name="cancel" value="Cancel" /> <input
				type="reset" name="reset" value="Reset" /> <input type="submit"
				name="submit" value="Submit" />
		</div>
	</form:form>
</body>
</html>