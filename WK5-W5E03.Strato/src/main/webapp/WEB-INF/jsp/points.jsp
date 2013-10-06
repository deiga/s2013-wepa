<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Observation points</title>
    </head>
    <body>

        <h1>Observation points</h1>

        <div>
            <c:forEach var="point" items="${points}">
                <p>${point.name} (lat: ${point.lat}, lon: ${point.lon}) (<a href="http://maps.google.com/?q=${point.lat},${point.lon}" target="_blank">map</a>)</p>
            </c:forEach>
        </div>

        <h2>Add new</h2>            
        <div>
            <form:form commandName="observationPoint" action="${pageContext.request.contextPath}/app/observationpoint" method="POST">
                <p>  
                    <form:input path="name" />  
                    <label for="name">Name</label>  
                    <label><form:errors path="name" /></label>
                </p>  
                <p>  
                    <form:input path="latitude" />  
                    <label for="latitude">Latitude (Attn! Use latitude * 1000000)</label>
                    <label><form:errors path="latitude" /></label>
                </p>  
                <p>  
                    <form:input path="longitude" />
                    <label for="longitude">Longitude (Attn! Use longitude * 1000000)</label>
                    <label><form:errors path="longitude" /></label>
                </p>
                <p>  
                    <input type="submit" value="Add" />  
                </p>
            </form:form>
        </div>


        <div>
            <p><a href="observation">Observations</a></p>
        </div>

        <div>
            <p><a href="observationpoint">Observation points</a></p>
        </div>
    </body>
</html>
