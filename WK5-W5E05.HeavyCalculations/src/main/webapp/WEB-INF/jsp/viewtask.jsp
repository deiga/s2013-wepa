<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task information</title>
    </head>
    <body>
        <h1>Task ${task.id} ${message}</h1>

        <p>Status: ${task.status}</p>

        <p>Result:</p>

        <p>${task.result}</p>

    </body>
</html>
