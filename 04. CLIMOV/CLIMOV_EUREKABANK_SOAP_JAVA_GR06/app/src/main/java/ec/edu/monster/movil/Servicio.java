package ec.edu.monster.movil;

import android.util.Xml;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

/**
 * Cliente del servicio SOAP WS_EUREKA (Payara). Mismas operaciones que la consola y el escritorio:
 * login, consultarSaldo, regDeposito, regRetiro, regTransferencia y traerMovimientos.
 * El sobre SOAP se arma a mano para no depender de librerías (ksoap2, etc.).
 */
final class Servicio {

    static final String NODO = "SOAP · JAVA";
    /** Misma IP de Radmin VPN que usan la web y el escritorio. En el emulador: 10.0.2.2:8080. */
    static final String HOST_POR_DEFECTO = "26.243.233.245:8080";
    private static final String RUTA = "/WS_EUREKA/WSEureka";
    private static final String NS = "http://ws.monster.edu.ec/";

    static String host = HOST_POR_DEFECTO;

    private Servicio() { }

    static boolean login(String usuario, String clave) throws Exception {
        String r = llamar("login", campo("usuario", usuario) + campo("clave", clave));
        return Boolean.parseBoolean(leer(r, "estado").get(0).get("estado"));
    }

    static double saldo(String cuenta) throws Exception {
        String r = llamar("consultarSaldo", campo("cuenta", cuenta));
        return Double.parseDouble(leer(r, "saldo").get(0).get("saldo"));
    }

    static boolean deposito(String cuenta, double importe) throws Exception {
        return estado(llamar("regDeposito", campo("cuenta", cuenta) + campo("importe", numero(importe))));
    }

    static boolean retiro(String cuenta, double importe) throws Exception {
        return estado(llamar("regRetiro", campo("cuenta", cuenta) + campo("importe", numero(importe))));
    }

    static boolean transferencia(String origen, String destino, double importe) throws Exception {
        return estado(llamar("regTransferencia",
                campo("cuentaOrigen", origen) + campo("cuentaDestino", destino) + campo("importe", numero(importe))));
    }

    /** Cada movimiento: {fecha, tipo, accion, importe}. */
    static List<String[]> movimientos(String cuenta) throws Exception {
        List<String[]> lista = new ArrayList<>();
        for (Map<String, String> m : leer(llamar("traerMovimientos", campo("cuenta", cuenta)), "movimiento")) {
            String fecha = m.get("fecha") == null ? "" : m.get("fecha");
            lista.add(new String[]{
                    fecha.length() >= 10 ? fecha.substring(0, 10) : fecha,
                    m.get("tipo"), m.get("accion"),
                    String.format(Locale.US, "%.2f", Double.parseDouble(m.get("importe")))});
        }
        return lista;
    }

    // ── SOAP ────────────────────────────────────────────────────────────

    private static String llamar(String operacion, String cuerpo) throws Exception {
        String sobre = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body>"
                + "<ns:" + operacion + " xmlns:ns=\"" + NS + "\">" + cuerpo + "</ns:" + operacion + ">"
                + "</S:Body></S:Envelope>";
        HttpURLConnection c = (HttpURLConnection) new URL("http://" + host + RUTA).openConnection();
        c.setConnectTimeout(8000);
        c.setReadTimeout(15000);
        c.setRequestMethod("POST");
        c.setDoOutput(true);
        c.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        c.setRequestProperty("SOAPAction", "\"\"");
        try (OutputStream os = c.getOutputStream()) {
            os.write(sobre.getBytes(StandardCharsets.UTF_8));
        }
        int codigo = c.getResponseCode();
        String respuesta = texto(codigo < 400 ? c.getInputStream() : c.getErrorStream());
        if (codigo >= 400) {
            List<Map<String, String>> fault = leer(respuesta, "Fault");
            String msg = fault.isEmpty() ? null : fault.get(0).get("faultstring");
            throw new Exception(msg != null ? msg : "El servidor respondió HTTP " + codigo);
        }
        return respuesta;
    }

    /** Busca cada elemento {@code etiqueta} y devuelve el texto de sus hijos (o el suyo propio). */
    private static List<Map<String, String>> leer(String xml, String etiqueta) throws Exception {
        XmlPullParser p = Xml.newPullParser();
        p.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true); // "S:Fault" → "Fault"
        p.setInput(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), "UTF-8");
        List<Map<String, String>> lista = new ArrayList<>();
        Map<String, String> actual = null;
        int nivel = 0;
        for (int ev = p.getEventType(); ev != XmlPullParser.END_DOCUMENT; ev = p.next()) {
            if (ev == XmlPullParser.START_TAG) {
                if (actual == null && p.getName().equals(etiqueta)) {
                    actual = new HashMap<>();
                    lista.add(actual);
                    nivel = p.getDepth();
                } else if (actual != null) {
                    actual.put(p.getName(), p.nextText());
                }
            } else if (ev == XmlPullParser.TEXT && actual != null && p.getDepth() == nivel && !p.isWhitespace()) {
                actual.put(etiqueta, p.getText());      // elemento simple, ej. <saldo>820.5</saldo>
            } else if (ev == XmlPullParser.END_TAG && actual != null && p.getDepth() == nivel) {
                actual = null;
            }
        }
        if (lista.isEmpty() && !etiqueta.equals("movimiento") && !etiqueta.equals("Fault")) {
            throw new Exception("Respuesta inesperada del servidor");
        }
        return lista;
    }

    private static boolean estado(String xml) throws Exception {
        return "1".equals(leer(xml, "estado").get(0).get("estado"));
    }

    private static String campo(String nombre, String valor) {
        String v = valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        return "<" + nombre + ">" + v + "</" + nombre + ">";
    }

    private static String numero(double d) {
        return String.format(Locale.US, "%.2f", d);
    }

    private static String texto(InputStream in) throws Exception {
        if (in == null) return "";
        try (InputStream is = in) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            for (int n; (n = is.read(buf)) > 0; ) out.write(buf, 0, n);
            return out.toString("UTF-8");
        }
    }
}
