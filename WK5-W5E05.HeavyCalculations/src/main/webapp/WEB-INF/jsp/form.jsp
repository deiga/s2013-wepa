<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submit a task</title>
    </head>
    <body>
        <h1>Enter your data for processing:</h1>

        <div>
            <form:form commandName="task" action="${pageContext.request.contextPath}/app/" method="POST" >
                Data to process: <form:input path="data" /> <form:errors path="data" /><br/>
                <input type="submit"/>
            </form:form>
        </div>
    </body>
</html>
