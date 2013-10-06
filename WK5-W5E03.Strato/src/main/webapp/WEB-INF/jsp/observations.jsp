<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Observations</title>
    </head>
    <body>
        <h1>Observations</h1>

        <div>
            <c:forEach var="observation" items="${observations}">
                <p><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${observation.timestamp}"/> ${observation.observationPoint.name}: ${observation.celsius} Celsius</p>
            </c:forEach>

            <c:if test="${pageNumber > 1}">
                <a href="${pageContext.request.contextPath}/app/observation?pageNumber=${pageNumber - 1}">Prev</a>
            </c:if>

            <c:if test="${pageNumber < totalPages}">
                <a href="${pageContext.request.contextPath}/app/observation?pageNumber=${pageNumber + 1}">Next</a>
            </c:if>
        </div>

        <h2>Add new observation</h2>            
        <div>
            <form:form commandName="formObject" action="${pageContext.request.contextPath}/app/observation" method="POST" >
                <p>
                    <form:select path="observationPointId" items="${observationpoints}" itemValue="id" itemLabel="name"/>
                    <label for="observationPointId">Location</label>  
                    <form:errors path="observationPointId" />  
                </p>  
                <p>  
                    <form:input path="celsius" id="celsius" />  
                    <label for="celsius">Celsius</label> 
                    <form:errors path="celsius" />  
                </p>
                <p class="submit">  
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
