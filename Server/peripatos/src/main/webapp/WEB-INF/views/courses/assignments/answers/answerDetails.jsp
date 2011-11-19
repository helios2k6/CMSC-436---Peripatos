<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<body>
	<table>
		<c:forEach items="${answers}" var="answer">
			<tr><td>${answer.body}</td></tr>
		</c:forEach>
	</table>
</body>
</html>