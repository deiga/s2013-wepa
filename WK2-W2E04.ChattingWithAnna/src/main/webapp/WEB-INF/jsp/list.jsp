<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chatting with Anna</title>
    </head>
    <body>
        <h1>Chatting with Anna</h1>

        <form action="${pageContext.request.contextPath}/app/add-message" method="POST">
            Type a question in English for Anna, our virtual assistant:<br/>
            <input type="text" name="message" id="message" />
            <input type="submit" id="submit" value="Ask" />
        </form>

        <hr/>

        <h2>Messages</h2>

        <c:forEach var="message" items="${messages}">
            <p>${message}</p>
        </c:forEach>
    </body>
</html>
