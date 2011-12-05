<%@include file="/WEB-INF/views/header.jsp"%>

<script type="text/javascript">
	var questionDictionary = {}

	$(function() {
		//Set Date Picker for the due date thing
		$("#dueDate").datepicker();

		//Set droppable
		$("#assignedQuestionsList, #availableQuestionsList").sortable({
			connectWith : ".connectedSortable"
		}).disableSelection();
		
		//Hydrate the questionDictionary
		$("#assignedQuestionsList > li").each(function(index, element){
			
		});
		
	});

	function processSelectedQuestions() {
		var postString = "";
		$("#assignedQuestionsList > li").each(function(index, element) {
			//Process stuff here
			if (index == 0) {
				postString = $(element).text();
			} else {
				postString = postString + ",;" + $(element).text();
			}
		});

		return postString;
	}

	function preSubmitFunction() {
		var postString = processSelectedQuestions();

		$('input[name="selectedQuestions"]').val(postString);
	}
</script>
</head>
<body>
	<div class="mainBodyAreaClass">
		<div class="formClass">
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
				<table>
					<tr>
						<th>Assigned Questions</th>
						<th>Available Questions</th>
					</tr>
					<tr>
						<td>
							<div class="assignmentQuestionEntryClass">
								<ul id="assignedQuestionsList" class="connectedSortable">
									<c:forEach items="${assignment.questions}"
										var="assignedQuestion">
										<li>${assignedQuestion.title}[${assignedQuestion.id}]</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td>
							<div class="assignmentQuestionEntryClass">
								<ul id="availableQuestionsList" class="connectedSortable">
									<c:forEach items="${availableQuestions}"
										var="availableQuestion">
										<li>${availableQuestion.title}[${availableQuestion.id}]</li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</table>
				<div id="formFooter">
					<input type="submit" name="submit" value="Save"
						onClick="preSubmitFunction()" /> <input type="button"
						name="cancel" value="Cancel" />
				</div>
				<input type="hidden" name="selectedQuestions" value="" />
			</form:form>
			<form:form method="DELETE">
				<input type="submit" value="Delete" />
			</form:form>
		</div>
	</div>
</body>
</html>