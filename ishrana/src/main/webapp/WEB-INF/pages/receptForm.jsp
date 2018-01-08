<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recept</title>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="includes/header.jsp" />
<h1>Unesi/izmeni recept</h1>

<c:url var="action" value="/recepti" />
<form:form modelAttribute="recept" method="POST"  action="${action}" enctype="multipart/form-data">
    <table>
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
            <td>NAMIRNICA</td>
            <td>KOLIČINA (g)</td>
        </tr>

        <c:if test="${fn:length(recept.listaNamirnica) gt 0}">
            <c:forEach begin="0" end="${fn:length(recept.listaNamirnica)-1}" varStatus="status">
                <form:hidden path="listaNamirnica[${status.index}].naziv" />
                <form:hidden path="listaNamirnica[${status.index}].kcal" />
                <form:hidden path="listaNamirnica[${status.index}].p" />
                <form:hidden path="listaNamirnica[${status.index}].m" />
                <form:hidden path="listaNamirnica[${status.index}].uh" />
                <form:hidden path="listaNamirnica[${status.index}].kategorija" />
                <form:hidden path="listaNamirnica[${status.index}].namirnica_id" />

                <tr>
                    <td><form:label path="listaNamirnica[${status.index}].naziv"> ${recept.listaNamirnica[status.index].naziv} </form:label></td>
                    <td><form:input type="number" path="listaKolicina[${status.index}]" min="0.0" value="${recept.listaKolicina[status.index]}"/></td>
                    <td>
                        <button type="submit" name="removeNamirnica"
                                value="${recept.listaNamirnica[status.index].namirnica_id}">obriši</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>


        <c:if test="${not empty namirniceSelect}">
        <tr>
            <td><form:select path="" name="nid">
                <%-- <option value="0">--- Select ---</option> --%>
                <form:options items="${namirniceSelect}" itemLabel="naziv" itemValue="namirnica_id" />
                </form:select>
            </td>
            <td><input type="number" name = "kolicina"  min="0.0" /></td>
            <td>
                <button type="submit" name="addNamirnica">dodaj</button>
            </td>
        </tr>
        </c:if>


        <tr>
                <%--<td>msgSlikaRemoved: ${msgSlikaRemoved}</td>
                   <input type="hidden" name="msgSlikaRemoved" value="${msgSlikaRemoved}">--%>
            <td>msgSlikaExists: ${msgSlikaExists}</td>
            <td>${msgSlikaNotExist}</td>
            <input type="hidden" name="msgSlikaExists" value="${msgSlikaExists}">
        </tr>


        <c:choose>
            <%-- <c:when test="${fn:length(recept.slika) gt 0 or not empty msgSlikaExists}"> --%>
            <c:when test="${not empty msgSlikaExists}">
                <tr>
                    <td>
                        <img src="<c:url value='/recepti/tempSlika' />" height="70"/>
                    </td>
                    <td>
                        <button type="submit" name="removeSlika"
                                value="${recept.recept_id}">obriši sliku</button>
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td><form:label path="slika">Slika:</form:label></td>
                    <td><form:input type="file" path="slika" name="slika" /></td>
                    <td><button type="submit" name="addSlika"
                                value="${recept.recept_id}">Snimi sliku</button></td>
                </tr>
            </c:otherwise>
        </c:choose>


        <tr>
            <td>
                <button type="submit" name="save_button" class="button">Snimi</button>
            </td>
            <td>
                <button type="submit" name="cancel_button" class="button">Odustani</button>
            </td>
        </tr>
    </table>

</form:form>
<jsp:include page="includes/footer.jsp" />
</body>
</html>