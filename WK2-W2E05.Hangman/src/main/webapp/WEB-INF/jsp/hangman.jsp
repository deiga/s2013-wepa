<%-- 
    Document   : hangman
    Created on : Sep 17, 2013, 6:05:13 PM
    Author     : timosand
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hangman</title>
    </head>
    <body>
        <h1>${word}</h1>
        <h2>Status: ${state}</h2>
        <h2>Used characters: [${fn:join(used,",")}]</h2>
        <p>
            <img src="${pageContext.request.contextPath}/img/hangman-${error}.png" />
        </p>
        <form action="${pageContext.request.contextPath}/app/guess" method="POST">
            <label>Your guess: <input type="text" id="guess" name="guess" /></label>
            <br />
            <label>Current game id: <input type="text" id="gameid" name="gameid" value="${gameid}"/> </label>
            <br />
            <input type="submit" />
        </form>
        <a href="start">New Game</a>
    </body>
</html>
