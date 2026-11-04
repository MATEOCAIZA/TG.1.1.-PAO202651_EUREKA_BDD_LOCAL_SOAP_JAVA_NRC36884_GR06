<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consultar Saldo</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/eureka-hud.css">
</head>
<body>
    <div class="container">
        <img src="images/hud.jpg" alt="Sullivan" class="logo"/>
        <h2>Consulta de Saldo</h2>

        <form action="ConsultarSaldoServlet" method="post">
            <input type="text" name="cuenta" placeholder="Número de cuenta"><br>
            <input type="submit" value="Consultar">
        </form>

        <hr/>

        <%
            String error = (String) request.getAttribute("error");
            Double saldo = (Double) request.getAttribute("saldo");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            } else if (saldo != null) {
        %>
            <p class="success">💰 Saldo actual: $<%= String.format("%.2f", saldo) %></p>
        <%
            }
        %>

        <br/>
        <a href="menu.jsp"><button>Volver al Menú</button></a>
    </div>
</body>
</html>
