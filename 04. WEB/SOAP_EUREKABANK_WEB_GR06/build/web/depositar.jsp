<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Depósito de Dinero</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Depósito de Dinero</h2>
        <form action="DepositoServlet" method="post">
            <input type="text" name="cuenta" placeholder="Cuenta"><br>
            <input type="text" name="empleado" placeholder="Código de empleado"><br>
            <input type="text" name="importe" placeholder="Importe a depositar"><br>
            <input type="submit" value="Depositar">
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
