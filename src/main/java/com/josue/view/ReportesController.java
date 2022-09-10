package com.josue.view;

import com.josue.reportes.Reportes;
import javafx.event.ActionEvent;

import java.util.HashMap;

public class ReportesController {
    public void mostrarProductos(ActionEvent actionEvent) throws Exception {
        var parametros = new HashMap<String, Object>();
        parametros.put("primer_nombre", "Victor");
        parametros.put("num_cedula", "61517109870001W");
        Reportes.generarReporte("reportes/Productos.jrxml", parametros);
    }
}
