<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retirar Dinero</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/eureka-hud.css">
</head>
<body>
    <div class="container">
        <h2>Retiro de Dinero</h2>
        <form action="RetiroServlet" method="post">
            <input type="text" name="cuenta" placeholder="Cuenta"><br>
            <input type="text" name="empleado" placeholder="Código de empleado"><br>
            <input type="text" name="importe" placeholder="Importe a retirar"><br>
            <input type="submit" value="Retirar">
        </form>

        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <p class="success"><%= mensaje %></p>
        <%
            }
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>
        <br/>
        <a href="menu.jsp"><button>Volver al Menú</button></a>
    </div>
</body>
</html>
