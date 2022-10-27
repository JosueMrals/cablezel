package com.josue.view;

import com.josue.modelo.Contrato;
import com.josue.modelo.DetalleFactura;
import com.josue.reportes.Reportes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FacturaSecundariaController implements Initializable {

     // logger de Log4j
    private static final Logger logger = LogManager.getLogger(FacturaSecundariaController.class);

    public TableView<DetalleFactura> tvMostrarPagos;
    public TableColumn<DetalleFactura, String> colN1;
    public TableColumn<DetalleFactura, String> colN2;
    public TableColumn<DetalleFactura, String > colN3;
    public TableColumn<DetalleFactura, String > colN4;
    public TableColumn<DetalleFactura, String > colN5;
    public Button btFacturar;

    public Button btImprimir;
    public TextArea txtDireccion;
    public TextField txtCliente;
    public TextField txtNumFactura;
    public TextField txtFechaFactura;
    public TextField txtNota;
    public Label lbTotal;
    public Label lbDescuento;
    public Label lbSubTotal;
    FacturarController facturarController;

    float total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void recibirDatos(FacturarController facturarPrincipalController , ObservableList<DetalleFactura> datos, float total){
        logger.info("Recibiendo datos de la factura principal");
        logger.info("Datos recibidos: " + datos);
        tvMostrarPagos.setItems(datos);
        this.total = total;
        this.facturarController = facturarPrincipalController;

        lbTotal.setText(String.valueOf(total));
        lbDescuento.setText(String.valueOf(total * 0.10));
        lbSubTotal.setText(String.valueOf(total - (total * 0.10)));

    }


    public void btCompletarClick(ActionEvent actionEvent) {
        // pagar
        facturarController.realizarPago();
        // cerrar la ventana actual
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void btImprimirClick(ActionEvent actionEvent) throws Exception {
        // HashMap para los parametros del reporte
        HashMap<String, Object> parametros = new HashMap<>();

        // obtener los datos de los detalles de la factura
        ObservableList<DetalleFactura> detalles = tvMostrarPagos.getItems();

        // obtener el cliente
        String cliente = txtCliente.getText();
        String direccion = txtDireccion.getText();

        // obtener la factura
        String factura = txtNumFactura.getText();

        // obtener la fecha
        String fecha = txtFechaFactura.getText();

        // establecer los parametros
        parametros.put("cliente", cliente);
        parametros.put("direccion", direccion);
        parametros.put("factura", factura);
        parametros.put("fecha", fecha);
        parametros.put("detalles", detalles);

        // imprimir el reporte
        Reportes.generarReporte("reportes/Productos.jrxml", parametros);

    }
}
