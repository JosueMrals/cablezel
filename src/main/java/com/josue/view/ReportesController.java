package com.josue.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

public class ReportesController {
    // logger of Log4j
    private static final Logger log = LogManager.getLogger(ReportesController.class);
    public void mostrarProductos() throws Exception {
        var parametros = new HashMap<String, Object>();
        parametros.put("primer_nombre", "Victor");
        parametros.put("num_cedula", "61517109870001W");
        //Reportes.generarReporte("reportes/Productos.jrxml", parametros);
    }

    public void mostrarPagos() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Pagos.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Pagos");
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Obtener los pagos del dia de hoy
    public void pagosDelDia() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Reportes/ReporteDiario.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Reporte Diario");
        stage.setScene(new Scene(root));
        stage.show();
    }

}