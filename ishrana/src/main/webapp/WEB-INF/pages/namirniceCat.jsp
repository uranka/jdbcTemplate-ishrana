<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/WEB-INF/pages/errorPageDelete.jsp" %>

<html>
<head>
    <title>Namirnice po kategoriji</title>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>

<jsp:include page="includes/header.jsp" />

<h1>Namirnice</h1>

<ul>
    <li><a href="${pageContext.request.contextPath}/namirnice/all" class="button-cat">sve</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/voće" class="button-cat">voće</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/povrće" class="button-cat">povrće</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/mleko i mlečni proizvodi" class="button-cat">mlečno</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/meso" class="button-cat">meso</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/masti" class="button-cat">masti</a></li>
    <li><a href="${pageContext.request.contextPath}/namirnice/žitarice" class="button-cat">žitarice</a></li>
</ul>

<table border="1">
    <tr>
        <th>naziv</th>
        <th>kcal</th>
        <th>proteini</th>
        <th>masti</th>
        <th>ugljeni-hidrati</th>
        <th>kategorija</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="namirnica" items="${namirnice}">
        <tr>
            <td>${namirnica.naziv}</td>
            <td>${namirnica.kcal}</td>
            <td>${namirnica.p}</td>
            <td>${namirnica.m}</td>
            <td>${namirnica.uh}</td>
            <td>${namirnica.kategorija}</td>
            <td><a href="${pageContext.request.contextPath}/namirnice/edit/${namirnica.namirnica_id}">izmeni</a></td>
            <td><a href="${pageContext.request.contextPath}/namirnice/remove/${namirnica.namirnica_id}">obriši</a></td>
        </tr>
    </c:forEach>
    <tr >
        <td colspan="7"></td>
        <td><a href="${pageContext.request.contextPath}/namirnice/add">dodaj</a></td>
    </tr>

</table>


<c:url var="action" value="/namirnice/${category}" />
<form action="${action}" method="post">
    <input type="hidden" name="firstRow" value="${firstRow}">
    <button type="submit" name="page" value="previous" class="button">&laquo;</button>
    <button type="submit" name="page" value="next" class="button">&raquo;</button>
</form>
<jsp:include page="includes/footer.jsp" />

</body>
</html>
