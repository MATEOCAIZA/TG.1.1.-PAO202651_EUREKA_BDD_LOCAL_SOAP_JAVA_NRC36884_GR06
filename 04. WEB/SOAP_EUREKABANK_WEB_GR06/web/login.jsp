<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Monsters Inc</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/eureka-hud.css">
</head>
<body>
    <div class="container">
        <img src="images/hud.jpg" alt="Sullivan" class="logo"/>
        <h2>Ingreso al Banco - Monsters Inc</h2>
        <form action="LoginServlet" method="post">
            <input type="text" name="usuario" placeholder="Usuario"><br>
            <input type="password" name="clave" placeholder="Contraseña"><br>
            <input type="submit" value="Ingresar">
        </form>
        <p class="error">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
        </p>
    </div>
</body>
</html>
