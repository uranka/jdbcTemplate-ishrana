<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <title>Namirnica edit</title>
</head>
<body>
<h1>Unesi/izmeni namirnicu</h1>

<c:url var="action" value="/namirnice" />
<form:form modelAttribute="namirnica" method="POST"  action="${action}">

    <c:out value="${action}"/>-----${action} <br/>
    naziv: ${namirnica.naziv}  kcal: ${namirnica.kcal}   namirnica_id: ${namirnica.namirnica_id}

<table border="1" cellpadding="10">
    <tr>
        <td><form:label path="naziv">Naziv</form:label></td>
        <td><form:input path="naziv"/></td>
    </tr>

    <%--  skriveno polje koje prenosi id namirnice --%>
    <form:hidden path="namirnica_id" value="${namirnica.namirnica_id}"/>


    <%--
        <form:hidden path="namirnica_id" value="555"/>
    --%>

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
                <%--   <input type="submit" name="save_button" value="Snimi" />   --%>
                <button type="submit" name="save_button">Snimi</button>
            </td>
            <td>
                <%-- <input type="submit" name="cancel_button" value="Odustani" />  --%>
                <button type="submit" name="cancel_button">Odustani</button>
            </td>

        </tr>
    </table>
    </form:form>

    </body>
    </html>
