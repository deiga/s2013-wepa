<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Magic 8-Ball</title>
    </head>
    <body>
        
        <p><strong>Your question was: ${question}</strong></p>
        <p><strong>My answer is: ${answer}</strong></p>

        <p>&nbsp;</p>


        <p><strong>Ask again and I shall answer!</strong></p>
        <form action="${pageContext.request.contextPath}/app/ask" method="POST">
            <input type="text" name="question"/>
            <input type="submit" value="Submit"/>
        </form>

    </body>
</html>
