<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Family Album</title>
    </head>
    <body>
        <h1>Family Album</h1>

        <h2>Send new image to the album</h2>

        <form action="${pageContext.request.contextPath}/app/images" method="POST" enctype="multipart/form-data">
            <p>Description for image:</p>
            <textarea type="description" name="description" id="description" cols="50" rows="6"></textarea>
            <br/><br/>
            <p>Select file to send:</p>
            <input type="file" name="file" id="file" size="50" />
            <br/><br/>
            <input type="submit" id="submit" value="Send" />
        </form>

        <hr/>

        <h2>Images in the album</h2>

        <c:forEach var="image" items="${images}">
            <p>Image ${image.id} (<a href="${pageContext.request.contextPath}/app/images/${image.id}/download/attachment">download</a>) sent at ${image.timestamp}</p>
            <h4>Description:</h4>
            <p>${image.description}</p>
            <p><a href="${pageContext.request.contextPath}/app/images/${image.id}/download"><img src="${pageContext.request.contextPath}/app/images/${image.id}/download" alt="${image.description}" /></a></p>
            <hr />
        </c:forEach>
    </body>
</html>
