<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movies</title>
    </head>
    <body>
        <h1>Movies</h1>

        <h2>Add new movie</h2>

        <div>
            Enter name and length and press Submit:<br/>
            <form action="${pageContext.request.contextPath}/app/movies" method="POST">
                Name: <input type="text" name="name" id="name"/><br/>
                Length: <input type="text" name="lengthInMinutes" id="lengthInMinutes"/><br/>
                <input type="submit"/>            
            </form>
        </div>

        <h2>Current movies</h2>

        <ol>
            <c:forEach var="movie" items="${movies}">
                <li><c:out value="${movie.name}"/> (<c:out value="${movie.lengthInMinutes}"/> min)
                    <form method="POST" action="${pageContext.request.contextPath}/app/movies/<c:out value="${movie.id}"/>/delete">
                        <input type="submit" value="Remove" id="remove-<c:out value="${movie.id}"/>"/>
                    </form><br/>

                    Actors:</br>
                    <ul>
                        <c:forEach var="actor" items="${movie.actors}">
                            <li><c:out value="${actor.name}" /></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ol>

        <div><a href="${pageContext.request.contextPath}/app/actors">Actors</a></div>
    </body>
</html>
