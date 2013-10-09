<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title><spring:message code="label.question" /></title>

    </head>
    <body>

        <h1><spring:message code="label.thatsOkay" /></h1>

        <a href="${pageContext.request.contextPath}/app/"><spring:message code="label.answerAgain" /></a>
    </body>
</html>
