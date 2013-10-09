<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Access Granted</title>
    </head>
    <body>
        <h1>Yeehaw!</h1>
        
        <div>
            <img src="${pageContext.request.contextPath}/secret/images/guessed.jpg"/>
        </div>
        
        
        <p><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout!</a></p>
    </body>
</html>
