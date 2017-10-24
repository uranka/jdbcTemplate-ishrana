<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Namirnice</title>
</head>
<body>
<h1>Namirnice</h1>
<table border="1" cellpadding="10">
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

</body>
</html>
