<%@include file="../header.jsp"%>
<body>
	<div class="pageTitle">Question Bank</div>
	<h1>Questions</h1>
	<div class="unorderedList">
		<ul>
			<c:forEach items="${questions}" var="question">
				<li><a href="<c:url value='${question.id}'/>">${question.title}</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>