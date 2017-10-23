<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>Namirnica edit</title>
</head>
<body>
<h1>Unesi/izmeni namirnicu</h1>

<form:form modelAttribute="namirnica" action="result.do" >
<table border="1" cellpadding="10">
    <tr>
        <td><form:label path="naziv">Naziv</form:label></td>
        <td><form:input path="naziv"/></td>
    </tr>

    <!-- da postavim skriveno polje koje prenosi id namirnice -->

    <tr>
        <td><form:label path="kcal">Energija(kcal):</form:label></td>
        <td><form:input type="number" path="kcal" step=".1" min="0.0" max="999.9"/></td>
    </tr>
    <tr>
        <td><form:label path="p">Proteini(g):</form:label></td>
        <td><form:input type="number" path="p" step=".1" min="0.0" max="99.9"/></td>
    </tr>
    <tr>
        <td><form:label path="m">Masti(g):</form:label></td>
        <td><form:input type="number" path="m" step=".1" min="0.0" max="99.9"/></td>
    </tr>
    <tr>
        <td><form:label path="uh">Ugljeni hidrati(g):</form:label></td>
        <td><form:input type="number" path="uh" step=".1" min="0.0" max="99.9"/></td>
    </tr>

    <tr>
        <td><form:label path="kategorija">Kategorija:</form:label></td>
        <td><form:select path="kategorija" items="${kategorije}"/></td>
    </tr>
    <tr>
        <td>
            <input type="submit" value="Snimi" />
        </td>
        <td>
            <input type="button" value="Odustani" />
        </td>
    </tr>
</table>
</form:form>

</body>
</html>
