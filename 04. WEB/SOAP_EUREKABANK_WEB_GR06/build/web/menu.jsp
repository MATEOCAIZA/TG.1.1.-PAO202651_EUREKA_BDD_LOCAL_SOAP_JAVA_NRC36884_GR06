<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Menú Principal</title>
    <link rel="stylesheet" href="css/style.css">
   
</head>
<body>
    <div class="container">
        <img src="images/sullivan.png" class="logo" alt="Sullivan"/>
        <h2>Bienvenido al Banco - Monsters Inc</h2>
        <p class="top-label">Usuario: <strong><%= usuario %></strong></p>

        <div class="menu-grid">
            <a href="consultarSaldo.jsp">
                <div class="menu-button">Consultar Saldo</div>
            </a>
            <a href="retirar.jsp">
                <div class="menu-button">Retirar Dinero</div>
            </a>
            <a href="depositar.jsp">
                <div class="menu-button">Depositar Dinero</div>
            </a>
            <a href="transferencia.jsp">
                <div class="menu-button">Transferir</div>
            </a>
            <a href="movimientos.jsp">
                <div class="menu-button">Consultar Movimientos</div>
            </a>
            <a href="login.jsp">
                <div class="menu-button">Cerrar Sesión</div>
            </a>
        </div>
    </div>
</body>
</html>
