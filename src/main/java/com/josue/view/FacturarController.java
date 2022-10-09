package com.josue.view;

import com.josue.modelo.Factura;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacturarController implements Initializable {
    public TextField txtBuscarCliente;
    public Button btnBuscar;
    public TableColumn<Factura, String> colUsuario;
    public TableColumn<Factura, String> colCliente;
    public TableColumn<Factura, String> colFecha;
    public TableColumn<Factura, String> colEstado;
    public TableColumn<Factura, String> colTotal;
    public TableColumn<Factura, String> colAccion;
    public Button btFacturar;
    public TableColumn<Factura, String> colCliente1;
    public TableColumn<Factura, String> colEstado1;
    public TableColumn<Factura, String> colTotal1;
    public TableColumn<Factura, String> colAccion1;
    public TableView<Factura> tvBuscarClientes;
    public TableView<Factura> tvBuscarClientes1;
    public TableColumn<Factura, String> colFecha2;
    public TableColumn<Factura, String> colServicio;
    public Label lblUsuario;
    public TableColumn<Factura, String> colServicio1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarFacturas();
        addFacturarButtonToTable();

    }

    public void llenarFacturas() {
        IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class,
                HibernateUtil.getSessionFactory());
        ObservableList<Factura> facturas = FXCollections.observableArrayList(facturaService.getAll());
        colServicio.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getUsuario().getNickusuario()));
        colCliente.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCliente().getPrimer_nombre()
                + " " + param.getValue().getCliente().getPrimer_apellido()
                + " " + param.getValue().getCliente().getSegundo_apellido()
                + " " + param.getValue().getCliente().getSegundo_nombre()));
        colFecha.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getFecha_factura().toString()));
        colEstado.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getEstado()));
        colTotal.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getTotal().toString()));
        tvBuscarClientes.setItems(facturas);
    }

    public void addFacturarButtonToTable() {
        TableColumn<Factura, Void> colBtn = new TableColumn<>("Facturar");

        colBtn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Facturar");

            {
                btn.setOnAction(event -> {
                    Factura factura = getTableView().getItems().get(getIndex());
                    System.out.println("selectedData: " + factura);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        tvBuscarClientes.getColumns().add(colBtn);
    }

}
