<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring MVC</title>
</head>
<body>

<h2>Spring MVC</h2>

<jsp:useBean id="list" scope="request" type="java.util.List"/>

<c:if test="${not empty list}">

    <ul>
        <c:forEach items="${list}" var="listValue">
            <li>ID: ${listValue.id}, Name: ${listValue.name}</li>
        </c:forEach>
    </ul>

</c:if>

</body>
</html>
