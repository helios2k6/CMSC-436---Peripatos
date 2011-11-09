<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
	<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title></title>
</head>	
<body>
	<c:forEach var="currentUser" items="${listOfUsers}">
		${currentUser.name} - ${currentUser.password}<br/>
	</c:forEach>
</body>
</html>