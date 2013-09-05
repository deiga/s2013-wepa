<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>First Html Form</title>
    </head>
    <body>
        <!-- toteuta lomake tähän -->
        <form action="${pageContext.request.contextPath}/view">
            <label for="">Nimi <br><input type="text" id="name" name="name"></label>
            <label for="">Osoite <br><input type="text" id="address" name="address"></label>
            <label for="">Lipputyyppi:
                <input type="radio" name="ticket" id="ticket-green" value="green"> Vihreä<br>
                <input type="radio" name="ticket" id="ticket-yellow" value="yellow"> Keltainen <br>
                <input type="radio" name="ticket" id="ticket-red" value="red"> Punainen <br>
            </label>
            <input type="submit" value="Tilaa">
        </form>

    </body>
</html>
