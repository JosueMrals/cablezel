package com.josue.view;

import com.josue.modelo.Contrato;
import com.josue.modelo.DetalleFactura;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
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
    FacturarController facturarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void recibirDatos(FacturarController facturarPrincipalController , ObservableList<DetalleFactura> datos){
        logger.info("Recibiendo datos de la factura principal");
        logger.info("Datos recibidos: " + datos);
        tvMostrarPagos.setItems(datos);
        this.facturarController = facturarPrincipalController;

    }


    public void btCompletarClick(ActionEvent actionEvent) {
        // pagar
        facturarController.realizarPago();
        // cerrar la ventana actual
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void btImprimirClick(ActionEvent actionEvent) {
    }
}
