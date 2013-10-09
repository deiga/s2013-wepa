<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="label.question" /></title>
    </head>
    <body>

        <h1><spring:message code="label.question" /></h1>

        <form action="${pageContext.request.contextPath}/app/" method="POST">
            <input type="radio" name="answer" value="yes" /> <spring:message code="label.yes" /><br>
            <input type="radio" name="answer" value="no" checked /> <spring:message code="label.no" /><br><br>
            <input type="submit" value="<spring:message code="label.answer" />" />
        </form>

            <p><spring:message code="label.answers" arguments="${counter}" /></p>

    </body>
</html>
