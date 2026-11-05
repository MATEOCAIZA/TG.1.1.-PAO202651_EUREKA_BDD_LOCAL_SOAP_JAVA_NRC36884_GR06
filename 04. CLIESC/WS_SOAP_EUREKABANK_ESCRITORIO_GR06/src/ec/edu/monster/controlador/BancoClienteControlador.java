package ec.edu.monster.controlador;

import ec.edu.monster.ws.Movimiento;
import ec.edu.monster.ws.WSEureka;
import ec.edu.monster.ws.WSEureka_Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BancoClienteControlador {

    private final WSEureka_Service servicio;
    private final WSEureka port;

    // IP:puerto del servidor SOAP; se cambia con -Dservidor=IP:8080 al ejecutar
    public static final String SERVIDOR = System.getProperty("servidor", "localhost:8080");

    public BancoClienteControlador() {
        try {
            servicio = new WSEureka_Service(new URL("http://" + SERVIDOR + "/WS_EUREKA/WSEureka?wsdl"));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Servidor inválido: " + SERVIDOR, e);
        }
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