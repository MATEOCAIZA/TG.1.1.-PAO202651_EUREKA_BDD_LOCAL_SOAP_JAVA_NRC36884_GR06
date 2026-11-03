<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Movimientos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="movimientos-page">
    <div class="container">
        <h2>Consulta de Movimientos</h2>
        <form method="post" action="MovimientosServlet">
            <input type="text" name="cuenta" placeholder="Ingrese número de cuenta" />
            <input type="submit" value="Buscar" />
        </form>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }

            String movimientosHtml = (String) request.getAttribute("movimientos");
            if (movimientosHtml != null && !movimientosHtml.trim().isEmpty()) {
        %>
            <div class="table-wrapper">
                <%= movimientosHtml %>
            </div>
        <%
            } else if (error == null) {
        %>
            <p class="error">No hay movimientos para esta cuenta.</p>
        <%
            }
        %>

        <br>
        <a href="menu.jsp"><button>Volver al menú</button></a>
    </div>
</body>
</html>
