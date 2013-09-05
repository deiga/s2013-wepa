<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Albums</title>
    </head>
    <body>
        <h1>My Albums</h1>
        
        <!-- tee tehtävä tänne! -->
        <c:forEach var="albumName" items="${albums}">
            ${albumName.getName()}
            <br>
            <c:forEach var="trackName" items="${albumName.getTracks()}">
                 - ${trackName}
                <br>
            </c:forEach>
        </c:forEach>

    </body>
</html>
