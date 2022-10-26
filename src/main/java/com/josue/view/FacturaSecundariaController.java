package com.josue.view;

import com.josue.modelo.Contrato;
import com.josue.modelo.DetalleFactura;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacturaSecundariaController implements Initializable {

    public TableView<DetalleFactura> tvMostrarPagos;
    public TableColumn<DetalleFactura, String> colN1;
    public TableColumn<DetalleFactura, String> colN2;
    public TableColumn<DetalleFactura, String > colN3;
    public TableColumn<DetalleFactura, String > colN4;
    public TableColumn<DetalleFactura, String > colN5;
    public Button btFacturar;
    public TextArea txtDireccion;
    public TextField txtCliente;
    FacturarController facturarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void recibirDatos(FacturarController facturarPrincipalController , ObservableList<DetalleFactura> datos){
        tvMostrarPagos.setItems(datos);
        this.facturarController = facturarPrincipalController;
    }


}
