package ec.edu.monster.ws;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "WSEureka")
public class WSEureka {

    @WebMethod(operationName = "traerMovimientos")
    @WebResult(name = "movimiento")
    public List<Movimiento> traerMovimientos(
            @WebParam(name = "cuenta") String cuenta) {

        try {
            EurekaService service = new EurekaService();
            return service.leerMovimientos(cuenta);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @WebMethod(operationName = "regDeposito")
    @WebResult(name = "estado")
    public int regDeposito(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe) {

        try {
            EurekaService service = new EurekaService();
            service.registrarDeposito(cuenta, importe, "0001");
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @WebMethod(operationName = "regRetiro")
    @WebResult(name = "estado")
    public int regRetiro(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe) {

        try {
            EurekaService service = new EurekaService();
            service.registrarRetiro(cuenta, importe, "0001");
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @WebMethod(operationName = "regTransferencia")
    @WebResult(name = "estado")
    public int regTransferencia(
            @WebParam(name = "cuentaOrigen") String cuentaOrigen,
            @WebParam(name = "cuentaDestino") String cuentaDestino,
            @WebParam(name = "importe") double importe) {

        try {
            EurekaService service = new EurekaService();

            service.registrarTransferencia(
                    cuentaOrigen,
                    cuentaDestino,
                    importe,
                    "0001");

            return 1;

        } catch (Exception e) {
            return -1;
        }
    }

    @WebMethod(operationName = "consultarSaldo")
    @WebResult(name = "saldo")
    public double consultarSaldo(
            @WebParam(name = "cuenta") String cuenta) {

        try {
            EurekaService service = new EurekaService();
            return service.consultarSaldo(cuenta);
        } catch (Exception e) {
            return -1;
        }
    }

    @WebMethod(operationName = "login")
    @WebResult(name = "estado")
    public boolean login(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "clave") String clave) {

        try {
            EurekaService service = new EurekaService();
            return service.login(usuario, clave);
        } catch (Exception e) {
            return false;
        }
    }
}