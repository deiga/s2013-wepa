<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>You better get some REST</title>
    </head>
    <body>

        <h1>You better get some REST</h1>

        <ul>
            <li><a href="${pageContext.request.contextPath}/app/sleeps">List all</a></li>
        </ul>

        <h2>New sleep</h2>

        <p>Example of date format: 25.9.2013 03.30</p>

        <form:form commandName="sleep" action="/app/sleeps" method="POST">
            <form:input path="start" id="start"/> <form:errors path="start" />
            <form:input path="end" id="end"/> <form:errors path="end" />
            <form:input path="feeling" id="feeling"/> <form:errors path="feeling" />
            <input type="submit" />
        </form:form>


    </body>
</html>