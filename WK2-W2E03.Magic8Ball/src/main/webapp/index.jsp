<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Magic 8-Ball</title>
    </head>
    <body>
        <h1>Magic 8-Ball</h1>

        <p><strong>Ask and I shall answer!</strong></p>
        <form action="${pageContext.request.contextPath}/app/ask" method="POST">
            <input type="text" name="question"/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
