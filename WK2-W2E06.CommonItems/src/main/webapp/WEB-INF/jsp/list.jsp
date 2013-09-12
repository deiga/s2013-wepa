<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Everybody's Items</title>
    </head>
    <body>
        <h1>Everybody's Items</h1>

        <c:forEach var="item" items="${items}">
            <p>${item.name}: ${item.description}</p>
            <form method="POST" action="${pageContext.request.contextPath}/app/delete">
                <input type="hidden" name="id" value="${item.id}"/><input type="submit" value="Delete!"/>
            </form>
            <hr/>
        </c:forEach>

        <form method="POST" action="${pageContext.request.contextPath}/app/add">
            Name: <input type="text" name="name"/><br/>
            Desc: <input type="text" name="description"/><br/>
            <input type="submit" value="Add!"/>
        </form>

    </body>
</html>
