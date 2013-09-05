<%@page import="wad.eightball.Answers"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:if test="${param.question != null}">
            Your question was: ${param.question}
            <br>
            My answer is: <%= new Answers().getAnswer() %>
            <br><br>
        </c:if>
        <form action="" method="POST">
            <label for="">Ask and I shall answer! <br><input type="text" name="question"></label>
            <input type="submit">
        </form>
    </body>
</html>
