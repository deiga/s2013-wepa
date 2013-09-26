<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Beer page</title>
    </head>
    <body>
        <h1>Hello Beers!</h1>

        <c:if test="${not empty beer}">
            <h2>Inspecting ${beer.name}</h2>
            <form:form action="${pageContext.request.contextPath}/app/beers/${beer.id}" method="DELETE">
                <input type="submit" id="delete" value="DELETE"/>
            </form:form>

            <p><a href="${pageContext.request.contextPath}/app/beers">List</a></p>
        </c:if>

        <c:if test="${not empty beers}">
            <h2>Beers</h2>
            <div>
                <c:forEach var="beer" items="${beers}">                
                    <a href="${pageContext.request.contextPath}/app/beers/${beer.id}">${beer.name}</a><br/>
                </c:forEach>
            </div>

        </c:if>


        <h2>Add new</h2>            
        <div>
            <form:form action="${pageContext.request.contextPath}/app/beers" method="POST">
                <input type="text" id="name" name="name"/>
                <input type="submit" value="ADD!"/>
            </form:form>
        </div>
    </body>
</html>
