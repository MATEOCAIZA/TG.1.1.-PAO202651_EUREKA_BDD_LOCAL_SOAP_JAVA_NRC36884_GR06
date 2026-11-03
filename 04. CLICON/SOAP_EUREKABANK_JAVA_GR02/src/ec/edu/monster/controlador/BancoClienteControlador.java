package ec.edu.monster.controlador;

import ec.edu.monster.ws.Movimiento;
import ec.edu.monster.ws.WSEureka;
import ec.edu.monster.ws.WSEureka_Service;
import java.util.List;

public class BancoClienteControlador {


private WSEureka port;

public BancoClienteControlador() {
    WSEureka_Service servicio = new WSEureka_Service();
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

public boolean realizarTransferencia(String origen, String destino,
                                     double importe, String empleado) {
    return port.regTransferencia(origen, destino, importe) == 1;
}

public String consultarMovimientos(String cuenta) {

    List<Movimiento> lista = port.traerMovimientos(cuenta);

    StringBuilder sb = new StringBuilder();

    sb.append("\n");
    sb.append("====================================================\n");
    sb.append("MOVIMIENTOS DE LA CUENTA: ").append(cuenta).append("\n");
    sb.append("====================================================\n");

    for (Movimiento mov : lista) {

        sb.append("Nro: ").append(mov.getNromov())
          .append(" | Fecha: ").append(mov.getFecha())
          .append(" | Tipo: ").append(mov.getTipo())
          .append(" | Acción: ").append(mov.getAccion())
          .append(" | Importe: ").append(mov.getImporte())
          .append("\n");
    }

    sb.append("====================================================\n");
    sb.append("Total movimientos: ").append(lista.size()).append("\n");

    return sb.toString();
}


}
