<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le better Dance</title>
    </head>
    <body>
        <h1>Sign up for Le better Dance!</h1>

        <div>
            <form:form commandName="registration" action="${pageContext.request.contextPath}/app/register" method="POST" >
                Name: <form:input path="name" /> <form:errors path="name" /><br/>
                Address: <form:input path="address" /> <form:errors path="address" /><br/>
                Email: <form:input path="email" /> <form:errors path="email" /><br/>
                <input type="submit"/>
            </form:form>
        </div>
    </body>
</html>
