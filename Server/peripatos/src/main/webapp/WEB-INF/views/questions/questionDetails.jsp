<%@include file="../header.jsp"%>

</head>
<body>
	<h1>Question Details</h1>
	<div id="bodyArea">
		<form:form method="POST" modelAttribute="question">
			<div id="formBodyArea">
				<table>
					<tr>
						<td>Title:</td>
						<td><form:input path="title" value="${question.title}" /></td>
					</tr>
					<tr>
						<td>Body:</td>
						<td><form:textarea path="body" value="${question.body}"
								rows="5" cols="10" /></td>
					</tr>
					<tr>
						<td>Latitude:</td>
						<td><form:input path="location.latitude" value="${question.location.latitude}" /></td>
					</tr>
					<tr>
						<td>Longitude:</td>
						<td><form:input path="location.longitude"
								value="${question.location.longitude}" /></td>
					</tr>
				</table>
				<div id="questionMapArea"></div>
				<div id="formFooterArea">
					<input type="submit" name="saveQuestion" value="Save" /> <input
						type="button" name="cancelQuestion" value="Cancel" />
				</div>
			</div>
			
		</form:form>
		<form:form method="DELETE">
		<input type="submit" value="Delete"/>
		</form:form>
	</div>
</body>
</html>