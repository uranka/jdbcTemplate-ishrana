<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <img src = "<c:url value='/css/images/tri-apple-logo.png'/>" alt="nutrition-logo" />
    <h1>Nutrition Simplified </h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/namirnice/all">Namirnice</a></li>
            <li><a href="${pageContext.request.contextPath}/recepti/all">Recepti</a></li>
        </ul>
    </nav>
</header>
