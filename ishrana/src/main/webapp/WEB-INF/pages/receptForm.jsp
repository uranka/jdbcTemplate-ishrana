<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recept</title>
</head>
<body>

<h1>Unesi/izmeni recept</h1>

<c:url var="action" value="/recepti" />
<form:form modelAttribute="recept" method="POST"  action="${action}">
    <table border="1" cellpadding="10">
        <tr>
            <td><form:label path="naziv">Naziv</form:label></td>
            <td><form:input path="naziv"/></td>
        </tr>

        <form:hidden path="recept_id" value="${recept.recept_id}"/>


        <tr>
            <td><form:label path="vremePripreme">Vreme pripreme (min):</form:label></td>
            <td><form:input type="number" path="vremePripreme" min="0.0"/></td>
        </tr>
        <tr>
            <td><form:label path="vremeKuvanja">Vreme kuvanja (min):</form:label></td>
            <td><form:input type="number" path="vremeKuvanja" min="0.0"/></td>
        </tr>


        <tr>
            <td>
                <input type="submit" name="save_button" value="Snimi" />
            </td>
            <td>
                <input type="submit" name="cancel_button" value="Odustani" />
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>