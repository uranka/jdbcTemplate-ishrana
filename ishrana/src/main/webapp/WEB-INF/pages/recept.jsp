<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recept</title>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="includes/header.jsp" />
<h3>Detaljan prikaz recepta</h3>
<h1>${recept.naziv}</h1>

<img src="<c:url value='/recepti/photo/${recept.recept_id}' />" />

<p>Vreme pripreme: ${recept.vremePripreme} minuta</p>
<p>Vreme kuvanja: ${recept.vremeKuvanja} minuta</p>

<table >
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
<p>Ukupno kalorija:
<fmt:formatNumber type="number" maxFractionDigits="2" value="${map.kcalUkupno}" />
</p>
<p>Ukupno proteina:
    <fmt:formatNumber type="number" maxFractionDigits="2" value="${map.pUkupno}" />
</p>
<p>Ukupno masti:
    <fmt:formatNumber type="number" maxFractionDigits="2" value="${map.mUkupno}" />
</p>
<p>Ukupno ugljenih hidrata:
    <fmt:formatNumber type="number" maxFractionDigits="2" value="${map.uhUkupno}" />
</p>


<div id="procenti">
    <div id="p"></div>
    <div id="m"></div>
    <div id="uh"></div>
</div>

<jsp:include page="includes/footer.jsp" />

<script>
    document.getElementById("p").style.width = "${map.pProcenti}%";
    document.getElementById("m").style.width = "${map.mProcenti}%";
    document.getElementById("uh").style.width = "${map.uhProcenti}%";
</script>

</body>
</html>
