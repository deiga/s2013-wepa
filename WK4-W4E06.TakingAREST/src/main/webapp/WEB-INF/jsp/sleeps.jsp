<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>You better get some REST</title>
    </head>
    <body>
        
        <a href="${pageContext.request.contextPath}/app/">Back to index</a>
        
        <h1>Sleeps</h1>
        
        <ul>
            <c:forEach var="sleep" items="${sleeps}">
                <li><a href="${pageContext.request.contextPath}/app/sleeps/${sleep.id}">Sleep with ID ${sleep.id}</a></li>
            </c:forEach>
        </ul>
        
    </body>
</html>