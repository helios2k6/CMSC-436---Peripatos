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
		$("#dueDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});

		//Set droppable
		$("#assignedQuestionsList, #availableQuestionsList").sortable({
			connectWith : ".connectedSortable",
			placeholder : "ui-state-highlight" //This is temporary
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

	function preSubmitFunction() {
		var assignedQuestionIds = "";

		$("#assignedQuestionsList > li").each(function(index, element) {
			var text = $(element).text();
			var id = questionDictionary[text];

			if (index == 0) {
				assignedQuestionIds = id;
			} else {
				assignedQuestionIds = assignedQuestionIds + ",;" + id;
			}
		});

		$('input[name="selectedQuestions"]').val(assignedQuestionIds);

	}
	
	function deleteAssignment() {
		$("<input>").attr({
			type: 'hidden',
			name: '_method',
			value: 'DELETE'
		}).appendTo("#assignment");
	}
	
	function cancelAssignment(){
		history.go(-1);
	}
</script>
</head>
<body>
	<div class="pageTitleClass">Assignment Details</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>
	<div id="customMainBodyArea">
		<div class="formClass">
			<form:form method="POST" modelAttribute="assignment">
				<table id="assignmentDetailsNameAndDateId">
					<tr>
						<td>Name:</td>
						<td class="fullWidthInputClass"><form:input path="name" value="${assignment.name}" /></td>
					</tr>
					<tr>
						<td>Date:</td>
						<fmt:formatDate var="formattedDate" pattern='yyyy-MM-dd' value='${assignment.dueDate}'/>
						<td class="fullWidthInputClass"><form:input path="dueDate" readonly="true" value="${formattedDate}" /></td>
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
									<c:forEach items="${assignment.questions}" var="assignedQuestion">
										<li class="draggableQuestionListItemClass">${assignedQuestion.title}</li>
									</c:forEach>
								</ul>
							</div>
						</td>
						<td>
							<div class="assignmentQuestionEntryClass">
								<ul id="availableQuestionsList" class="connectedSortable">
									<c:forEach items="${availableQuestions}" var="availableQuestion">
										<li class="draggableQuestionListItemClass">${availableQuestion.title}</li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</table>
				<div class="formFooterArea">
					<input type="submit" name="submit" value="Save" onClick="preSubmitFunction()" /> <input type="button"
						name="cancel" value="Cancel" onClick="cancelAssignment()" />
					<c:if test="${newAssignment != true}">
						<input type="submit" name="delete" value="Delete" onClick="deleteAssignment()" />
					</c:if>
				</div>
				<input type="hidden" name="selectedQuestions" value="" />
			</form:form>
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