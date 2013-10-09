<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parlez vous Français?</title>
    </head>
    <body>

        <h1>Parlez vous Français?</h1>

        <form action="${pageContext.request.contextPath}/app/" method="POST">
            <input type="radio" name="answer" value="yes" /> Oui<br>
            <input type="radio" name="answer" value="no" checked /> Non<br><br>
            <input type="submit" value="Répondre" />
        </form>

        <p>${counter} réponses</p>

    </body>
</html>
