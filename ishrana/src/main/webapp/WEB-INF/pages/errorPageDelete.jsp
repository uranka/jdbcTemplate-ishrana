<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Deletion error</title>
</head>
<body>

<p>Izazvali ste ${pageContext.exception}</p>
<h2>Verovatno ne možete obrisati namirnicu jer je ona upotrebljena u nekom receptu.</h2>
<h3><a href="${pageContext.request.contextPath}/namirnice/all">Vratite se nazad na namirnice</a></h3>
</body>
</html>
