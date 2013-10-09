<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Access Granted</title>
    </head>
    <body>
        <h1>Tiitinen List</h1>

        <ul>
            <c:forEach var="item" items="${list}">
                <li>(${item.id}) ${item.content}</li>

                <sec:authorize access="hasRole('admin')">
                    <form:form action="${pageContext.request.contextPath}/app/list/${item.id}" method="DELETE">
                        <input type="submit" value="DELETE">
                    </form:form>
                </sec:authorize>
            </c:forEach>
        </ul>

        <h2>Add new</h2>

        <form:form action="${pageContext.request.contextPath}/app/list" method="POST">
            <p>
                Content:</br>
                <input type="text" name="content"/>
                <input type="submit" value="ADD">
            </p>
        </form:form>

        <p><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout!</a></p>
    </body>
</html>
