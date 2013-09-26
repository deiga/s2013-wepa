<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le Dance</title>
    </head>
    <body>
        <h1>Sign up for Le Dance!</h1>

        <div>
            <form action="${pageContext.request.contextPath}/app/register" method="POST" >
                Name: <input name="name" value="${name}"/> ${nameError}<br/>
                Address: <input name="address" value="${address}"/> ${addressError}<br/>
                Email: <input name="email" value="${email}"/> ${emailError}<br/>

                <input type="submit"/>
            </form>
        </div>
    </body>
</html>
