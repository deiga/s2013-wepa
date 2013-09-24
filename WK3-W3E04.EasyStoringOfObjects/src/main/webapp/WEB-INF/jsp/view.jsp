<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Storage</title>
    </head>
    <body>
        <h1>Storage</h1>

        <h2>Add new item</h2>

        <div>
            Enter item name and press Submit:<br/>
            <form action="add" method="POST">
                <input type="text" name="name" id="name"/><input type="submit"/>            
            </form>
        </div>

        <h2>Current items</h2>

        <ol>
            <c:forEach var="item" items="${items}">
                <li>${item.name}: ${item.count}
                    <form action="increaseCount" method="POST">
                        <input type="hidden" name="itemId" value="${item.id}"/>
                        <input type="submit" id="increment-${item.id}" value="+"/>
                    </form>
                </li>
            </c:forEach>
        </ol>
    </body>
</html>
