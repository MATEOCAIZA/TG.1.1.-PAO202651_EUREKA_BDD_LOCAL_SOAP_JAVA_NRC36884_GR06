package ec.edu.monster.controlador;

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

    public List<Movimiento> consultarMovimientos(String cuenta) {
        return port.traerMovimientos(cuenta);
    }
}