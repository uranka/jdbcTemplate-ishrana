<%--
  Created by IntelliJ IDEA.
  User: Win10
  Date: 10/21/2017
  Time: 5:54 PM
  To change this template use File | Settings | File Templates.
--%>
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
    </tr>
    <tr>
        <td>${namirnice[0].naziv}</td>
        <td>${namirnice[0].kcal}</td>
        <td>${namirnice[0].p}</td>
        <td>${namirnice[0].m}</td>
        <td>${namirnice[0].uh}</td>
        <td>${namirnice[0].kategorija}</td>
    </tr>
    <tr>
        <td>${namirnice[1].naziv}</td>
        <td>${namirnice[1].kcal}</td>
        <td>${namirnice[1].p}</td>
        <td>${namirnice[1].m}</td>
        <td>${namirnice[1].uh}</td>
        <td>${namirnice[1].kategorija}</td>
    </tr>
</table>

</body>
</html>
