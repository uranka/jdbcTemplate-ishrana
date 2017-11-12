<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recept</title>
</head>
<body>
<h3>Detaljan prikaz recepta</h3>
<h1>${recept.naziv}</h1>
<p>Vreme pripreme: ${recept.vremePripreme} minuta</p>
<p>Vreme kuvanja: ${recept.vremeKuvanja} minuta</p>

<table border="1" cellpadding="10">
<tr>
    <td>NAMIRNICA</td>
    <td>KOLIČINA (g)</td>
</tr>

<c:if test="${fn:length(recept.listaNamirnica) gt 0}">
<c:forEach begin="0" end="${fn:length(recept.listaNamirnica)-1}" varStatus="status">
    <tr>
        <td>${recept.listaNamirnica[status.index].naziv}</td>
        <td>${recept.listaKolicina[status.index]}</td>
    </tr>
</c:forEach>
</c:if>
</table>

<h2>Analiza recepta</h2>
<p>Ukupno kalorija: ${map.kcalUkupno}</p>
<p>Ukupno proteina: ${map.pUkupno}</p>
<p>Ukupno masti: ${map.mUkupno}</p>
<p>Ukupno ugljenih hidrata: ${map.uhUkupno}</p>

</body>
</html>
