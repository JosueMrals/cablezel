package com.josue.view;

import com.josue.modelo.*;
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
    public TableColumn<DetalleFactura, String> colCliente;
    public TableColumn<DetalleFactura, String> colFecha;
    public TableColumn<DetalleFactura, String> colEstado;
    public TableColumn<DetalleFactura, String> colTotal;
    public TableColumn<Factura, String> colAccion;
    public Button btFacturar;
    public TableColumn<Factura, String> colCliente1;
    public TableColumn<Factura, String> colEstado1;
    public TableColumn<Factura, String> colTotal1;
    public TableColumn<Factura, String> colAccion1;
    public TableView<DetalleFactura> tvBuscarClientes;
    public TableView<Factura> tvBuscarClientes1;
    public TableColumn<Factura, String> colFecha2;
    public TableColumn<DetalleFactura, String> colServicio;
    public Label lblUsuario;
    public TableColumn<Factura, String> colServicio1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFacturarButtonToTable();
        crearFactura();
        llenarDetalleFacturas();
    }

    //Obtener lista de DetalleFactura
    private ObservableList<DetalleFactura> getDetalleFactura() {
        IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(detalleFacturaService.getAll());
    }

    public ObservableList<Servicio> obtenerServicios() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(servicioService.getAll());
    }

    public void crearFactura() {
        ObservableList<DetalleFactura> detalleFactura = getDetalleFactura();

        if (detalleFactura.isEmpty()) {
            IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class,
                    HibernateUtil.getSessionFactory());
            Factura factura = facturaService.getId(1L);

            IGenericService< Servicio> servicioService = new GenericServiceImpl<>(Servicio.class,
                    HibernateUtil.getSessionFactory());
            Servicio servicio = servicioService.getId(1L);

            DetalleFactura detalleFactura1 = new DetalleFactura();
            detalleFactura1.setFactura(factura);
            detalleFactura1.setServicio(servicio);
            detalleFactura1.setTotal_pagar(1000f);
            detalleFactura1.setDescripcion("Prueba");

            IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                    HibernateUtil.getSessionFactory());
            detalleFacturaService.save(detalleFactura1);
        }
    }

    public void llenarDetalleFacturas() {
        ObservableList<DetalleFactura> detalleFactura = getDetalleFactura();

        colServicio.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getServicio()
                .getNombre()));
        colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                .getCliente().getPrimer_nombre()
                + " " + cellData.getValue().getFactura().getCliente().getSegundo_nombre()
                + " " + cellData.getValue().getFactura().getCliente().getPrimer_apellido()
                + " " + cellData.getValue().getFactura().getCliente().getSegundo_apellido()));
        colFecha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                .getFecha_factura().toString()));
        colEstado.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                .getEstado()));
        colTotal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_pagar()
                .toString()));

        tvBuscarClientes.setItems(detalleFactura);
    }

    public void addFacturarButtonToTable() {
        colAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnFacturar = new Button("Facturar");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                btnFacturar.setStyle("-fx-background-color: #00b300; -fx-text-fill: white; -fx-cursor: hand;");
                setGraphic(btnFacturar);
                btnFacturar.setOnAction(event -> {
                    Factura factura = getTableView().getItems().get(getIndex());
                    System.out.println("Factura: " + factura.getId());
                });
            }
        });
    }

}
