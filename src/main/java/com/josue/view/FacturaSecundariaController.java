package com.josue.view;

import com.josue.modelo.Contrato;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacturaSecundariaController implements Initializable {

    public TableView<Contrato> tvMostrarContratos;
    public TableColumn<Contrato, String> colN1;
    public TableColumn<Contrato, String> colN2;
    public TableColumn<Contrato, String > colN3;
    public TableColumn<Contrato, String > colN4;
    public Button btFacturar;
    public TextArea txtDireccion;
    public TextField txtCliente;
    FacturarPrincipalController facturarPrincipalController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void recibirDatos(FacturarPrincipalController facturarPrincipalController , ObservableList<Contrato> datos){
        tvMostrarContratos.setItems(datos);
        this.facturarPrincipalController = facturarPrincipalController;
    }


}
