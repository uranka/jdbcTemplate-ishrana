<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recepti</title>
</head>
<body>
<h1>Recepti</h1>
<table border="1" cellpadding="10">
    <tr>
        <th>naziv</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="recept" items="${recepti}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/recepti/get/${recept.recept_id}">${recept.naziv}</a></td>
            <td><a href="${pageContext.request.contextPath}/recepti/edit/${recept.recept_id}">izmeni</a></td>
            <td><a href="${pageContext.request.contextPath}/recepti/remove/${recept.recept_id}">obriši</a></td>
        </tr>
    </c:forEach>

    <tr >
        <td colspan="2"></td>
        <td><a href="${pageContext.request.contextPath}/recepti/add">dodaj</a></td>
    </tr>
</table>



</body>
</html>
