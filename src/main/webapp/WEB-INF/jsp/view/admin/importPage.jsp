<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>


<jsp:include page="../fragments/adminHeader.jsp" />

<div class="container">

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">

			<jsp:include page="../fragments/menu.jsp" />

		</div>
	</nav>
</head>
<body>
<h>Hello</h>
<form action="${pageContext.request.contextPath}/admin/submitFile" method="post">
<label>Tableaux des inscriptions </label>
<input type="file" class="btn btn-default">
<button type="submit"></button>

</form>
</body>
</html>