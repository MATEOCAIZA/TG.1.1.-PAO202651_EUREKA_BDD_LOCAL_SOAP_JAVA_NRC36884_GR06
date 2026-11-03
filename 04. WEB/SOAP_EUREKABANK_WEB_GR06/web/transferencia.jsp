<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transferencia de Dinero</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Transferencia de Dinero</h2>
        <form action="TransferenciaServlet" method="post">
            <input type="text" name="origen" placeholder="Cuenta origen"><br>
            <input type="text" name="destino" placeholder="Cuenta destino"><br>
            <input type="text" name="empleado" placeholder="Código de empleado"><br>
            <input type="text" name="importe" placeholder="Importe a transferir"><br>
            <input type="submit" value="Transferir">
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
