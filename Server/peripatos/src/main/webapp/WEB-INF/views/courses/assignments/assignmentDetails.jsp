<%@include file="/WEB-INF/views/header.jsp"%>

<script type="text/javascript">
	var questionDictionary = {}

	$(function() {
		function processDictionaryHydration(index, element) {
			var mainString = $(element).text();
			var firstSegment = mainString.indexOf('[');
			var secondSegment = mainString.indexOf(']');

			//Get Data
			var questionTitle = mainString.substring(0, firstSegment);
			var questionId = mainString.substring(firstSegment + 1,
					secondSegment);

			//Hydrate dictionary
			questionDictionary[questionTitle] = questionId;
		}

		//Set Date Picker for the due date thing
		$("#dueDate").datepicker({dateFormat:'yy-mm-dd'});

		//Set droppable
		$("#assignedQuestionsList, #availableQuestionsList").sortable({
			connectWith : ".connectedSortable",
			placeholder: "ui-state-highlight" //This is temporary
		}).disableSelection();

		//Hydrate the questionDictionary
		$("#hiddenAvailableQuestionList > li").each(function(index, element) {
			processDictionaryHydration(index, element);
		});
		$("#hiddenAssignedQuestionList > li").each(function(index, element) {
			processDictionaryHydration(index, element);
		});
		$("#hiddenValueArea").remove();

	});
	
	function preSubmitFunction(){
		var assignedQuestionIds = "";
		
		$("#assignedQuestionsList > li").each(function(index, element){
			var text = $(element).text();
			var id = questionDictionary[text];
			
			if(index == 0){
				assignedQuestionIds = id;
			}else{
				assignedQuestionIds = assignedQuestionIds + ",;" + id;
			}
		});
		
		$('input[name="selectedQuestions"]').val(assignedQuestionIds);
		
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
						<td><form:input path="dueDate" readonly="true"
								value="${assignment.dueDate}" /></td>
					</tr>
				</table>
				<table class="tableClass">
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
										<li>${assignedQuestion.title}</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td>
							<div class="assignmentQuestionEntryClass">
								<ul id="availableQuestionsList" class="connectedSortable">
									<c:forEach items="${availableQuestions}"
										var="availableQuestion">
										<li>${availableQuestion.title}</li>
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
			<div class="deleteFormClass">
				<form:form method="DELETE">
					<input type="submit" value="Delete" />
				</form:form>
			</div>
		</div>
	</div>
	<div id="hiddenValueArea">
		<c:forEach items="${availableQuestions}" var="availableQuestion">
			<ul id="hiddenAvailableQuestionList">
				<li>${availableQuestion.title}[${availableQuestion.id}]</li>
			</ul>
		</c:forEach>
		<c:forEach items="${assignment.questions}" var="assignedQuestion">
			<ul id="hiddenAssignedQuestionList">
				<li>${assignedQuestion.title}[${assignedQuestion.id}]</li>
			</ul>
		</c:forEach>
	</div>
</body>
</html>