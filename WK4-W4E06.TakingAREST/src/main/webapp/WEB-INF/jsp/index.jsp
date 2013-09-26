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
        
        <!-- Lisää lomake uuden Sleepin luomiseen tähän -->
        
    
    </body>
</html>