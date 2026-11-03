package ec.edu.monster.modelo;

import ec.edu.monster.ws.Movimiento;
import ec.edu.monster.ws.WSEureka;
import ec.edu.monster.ws.WSEureka_Service;
import java.util.List;

public class BancoClienteControlador {

    private final WSEureka_Service servicio;
    private final WSEureka port;

    public BancoClienteControlador() {
        servicio = new WSEureka_Service();
        port = servicio.getWSEurekaPort();
    }

    public boolean login(String usuario, String clave) {
        return port.login(usuario, clave);
    }

    public double consultarSaldo(String cuenta) {
        return port.consultarSaldo(cuenta);
    }

    public boolean realizarDeposito(String cuenta, double importe, String empleado) {
        return port.regDeposito(cuenta, importe) == 1;
    }

    public boolean realizarRetiro(String cuenta, double importe, String empleado) {
        return port.regRetiro(cuenta, importe) == 1;
    }

    public boolean realizarTransferencia(String origen, String destino, double importe, String empleado) {
        return port.regTransferencia(origen, destino, importe) == 1;
    }

    public String consultarMovimientos(String cuenta) {

        List<Movimiento> lista = port.traerMovimientos(cuenta);

        StringBuilder sb = new StringBuilder();

        sb.append("<table border='1'>");
        sb.append("<tr>");
        sb.append("<th>Cuenta</th>");
        sb.append("<th>Nro</th>");
        sb.append("<th>Fecha</th>");
        sb.append("<th>Tipo</th>");
        sb.append("<th>Acción</th>");
        sb.append("<th>Importe</th>");
        sb.append("</tr>");

        for (Movimiento m : lista) {
            sb.append("<tr>");
            sb.append("<td>").append(m.getCuenta()).append("</td>");
            sb.append("<td>").append(m.getNromov()).append("</td>");
            sb.append("<td>").append(m.getFecha()).append("</td>");
            sb.append("<td>").append(m.getTipo()).append("</td>");
            sb.append("<td>").append(m.getAccion()).append("</td>");
            sb.append("<td>").append(m.getImporte()).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }
}