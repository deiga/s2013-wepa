<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello thar!</title>
    </head>
    <body>
        <div>
            <img src="${pageContext.request.contextPath}/public/images/no-pass.jpeg" />
        </div>

        <p><a href="${pageContext.request.contextPath}/app/secret">To the secret pages!</a></p>
    </body>
</html>
