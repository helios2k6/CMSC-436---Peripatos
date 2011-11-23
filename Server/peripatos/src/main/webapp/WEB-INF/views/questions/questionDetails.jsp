<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	</head>
	<body>
		<h1>Question Details</h1>
		<ul>
			<li>${question.title}</li>
			<li>${question.body}</li>
			<li>${question.location.latitude}</li>
			<li>${question.location.longitude}</li>
		</ul>
	</body>
</html>