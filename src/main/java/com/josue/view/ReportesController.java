package com.josue.view;

import com.josue.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;

public class ReportesController {

    // logger of Log4j
    private static final Logger logger = LogManager.getLogger(ReportesController.class);
    public void mostrarProductos() throws Exception {
        var parametros = new HashMap<String, Object>();
        parametros.put("primer_nombre", "Victor");
        parametros.put("num_cedula", "61517109870001W");
        //Reportes.generarReporte("reportes/Productos.jrxml", parametros);
    }

    public void mostrarPagos() {
        GlobalUtil.mostrarReportes("/fxml/Pagos.fxml", "Pagos");
    }

    // Obtener los pagos del dia de hoy
    public void pagosDelDia() {
        GlobalUtil.mostrarReportes("/fxml/Reportes/ReporteDiario.fxml" , "Reporte Diario");
    }

    public void reportesMensual() {
        GlobalUtil.mostrarReportes("/fxml/Reportes/ReporteMensual.fxml", "Reportes Mensuales");
    }

    public void clientesAvisar() {
        GlobalUtil.mostrarReportes("/fxml/Reportes/ClientesAvisar.fxml", "Clientes a Avisar");
    }

    public void cortes() {
        GlobalUtil.mostrarReportes("/fxml/Reportes/Cortes.fxml", "Cortes");
    }




}
