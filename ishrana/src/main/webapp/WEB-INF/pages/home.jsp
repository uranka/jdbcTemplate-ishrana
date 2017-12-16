<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="includes/header.jsp" />

<h2>Welcome to nutrition pages</h2>
<a href="${pageContext.request.contextPath}/namirnice/all">Namirnice</a>
<a href="${pageContext.request.contextPath}/recepti/all">Recepti</a>

<jsp:include page="includes/footer.jsp" />
</body>
</html>
