package ec.edu.monster.controlador;

import ec.edu.monster.modelo.BancoClienteControlador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        System.out.println("================================");
        System.out.println("Usuario recibido = " + usuario);
        System.out.println("Clave recibida = " + clave);

        try {

            BancoClienteControlador controlador = new BancoClienteControlador();

            boolean ok = controlador.login(usuario, clave);

            System.out.println("Resultado login = " + ok);

            if (ok) {

                System.out.println("ENTRO AL MENU");

                request.getSession().setAttribute("usuario", usuario);

                response.sendRedirect("menu.jsp");

            } else {

                System.out.println("LOGIN INCORRECTO");

                request.setAttribute("error", "Usuario o contraseña incorrectos");

                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {

            System.out.println("ERROR EN LOGIN");
            e.printStackTrace();

            request.setAttribute("error", e.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
}