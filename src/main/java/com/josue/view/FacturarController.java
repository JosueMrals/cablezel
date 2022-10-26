package com.josue.view;

import com.josue.modelo.*;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FacturarController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(FacturarController.class.getName());
    public TextField txtBuscarCliente;
    public Button btnBuscar;
    public TableColumn<DetalleFactura, String> colCliente;
    public TableColumn<DetalleFactura, String> colFecha;
    public TableColumn<DetalleFactura, String> colEstado;
    public TableColumn<DetalleFactura, String> colTotal;
    public TableColumn<DetalleFactura, String> colAccion;
    public Button btFacturar;
    public TableColumn<DetalleFactura, String> colCliente1;
    public TableColumn<DetalleFactura, String> colEstado1;
    public TableColumn<DetalleFactura, String> colTotal1;
    public TableColumn<DetalleFactura, String> colAccion1;
    public TableView<DetalleFactura> tvBuscarClientes;
    public TableView<DetalleFactura> tvBuscarClientes1;
    public TableColumn<DetalleFactura, String> colFecha2;
    public TableColumn<DetalleFactura, String> colServicio;
    public TableColumn<DetalleFactura, String> colServicio1;
    String[] clientesAutocomplete = {};
    Usuario usuario;
    FacturarController facturarController = this;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientesAutocomplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutocomplete);
        llenarDetalleFacturas();
        txtBuscarCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue);
        });

        addFacturarButtonToTable();
        addButtonDelete();
        // crearFactura();
        llenarDetalleFacturas();

        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        usuario = manejador.getUsuario();
    }

    public void buscarCliente() {
        String nombreCliente = txtBuscarCliente.getText();
        IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                HibernateUtil.getSessionFactory());
        ObservableList<DetalleFactura> detalleFacturas = FXCollections.observableArrayList(detalleFacturaService.
                getAll());
        ObservableList<DetalleFactura> detalleFacturasFiltradas = FXCollections.observableArrayList();
        for(DetalleFactura detallesFactura : detalleFacturas) {
            if((detallesFactura.getFactura().getCliente().getPrimer_nombre()
            + " " + detallesFactura.getFactura().getCliente().getSegundo_nombre()
            + " " + detallesFactura.getFactura().getCliente().getPrimer_apellido()
            + " " + detallesFactura.getFactura().getCliente().getSegundo_apellido()).contains(nombreCliente)) {
                detalleFacturasFiltradas.add(detallesFactura);
            }
        }
        tvBuscarClientes.setItems(detalleFacturasFiltradas);
    }

    //Obtener lista de DetalleFactura
    private ObservableList<DetalleFactura> getDetalleFactura() {
        IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                HibernateUtil.getSessionFactory());
        return detalleFacturaService.getAll().stream().filter(detalleFactura -> detalleFactura.getFactura().
                getEstado().equals("pendiente")).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void crearFactura() {
        try {
            ObservableList<DetalleFactura> detalleFactura = getDetalleFactura();

            if (detalleFactura.isEmpty()) {
                IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class,
                        HibernateUtil.getSessionFactory());
                Factura factura = facturaService.getId(1L);

                IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class,
                        HibernateUtil.getSessionFactory());
                Servicio servicio = servicioService.getId(1L);

                DetalleFactura detalleFactura1 = new DetalleFactura();
                detalleFactura1.setFactura(factura);
                detalleFactura1.setServicio(servicio);
                detalleFactura1.setTotal_pagar(0f);
                detalleFactura1.setDescripcion("Prueba");

                IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                        HibernateUtil.getSessionFactory());
                detalleFacturaService.save(detalleFactura1);
            }
        }
        catch (Exception e) {
            LOGGER.info(e.toString());
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
            final Button btnFacturar = new Button("Facturar");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnFacturar.setStyle("-fx-background-color: #00b300; -fx-text-fill: white; -fx-cursor: hand;");
                    btnFacturar.setOnAction(event -> {
                        DetalleFactura detalleFactura = getTableView().getItems().get(getIndex());
                        tvBuscarClientes1.getItems().add(detalleFactura);
                        colServicio1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getServicio()
                                .getNombre()));
                        colCliente1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                                .getCliente().getPrimer_nombre()
                                + " " + cellData.getValue().getFactura().getCliente().getSegundo_nombre()
                                + " " + cellData.getValue().getFactura().getCliente().getPrimer_apellido()
                                + " " + cellData.getValue().getFactura().getCliente().getSegundo_apellido()));
                        colFecha2.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                                .getFecha_factura().toString()));
                        colEstado1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                                .getEstado()));
                        colTotal1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_pagar()
                                .toString()));
                        tvBuscarClientes.getItems().remove(detalleFactura);
                    });
                    setGraphic(btnFacturar);
                }
            }
        });
    }

    public void addButtonDelete() {
        colAccion1.setCellFactory(param -> new TableCell<>() {
            final Button btnEliminar = new Button("Eliminar");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnEliminar.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-cursor: hand;");
                    btnEliminar.setOnAction(event -> {
                        DetalleFactura fact = getTableView().getItems().get(getIndex());
                        tvBuscarClientes.getItems().add(fact);
                        tvBuscarClientes1.refresh();
                        tvBuscarClientes1.getItems().remove(fact);
                    });
                    setGraphic(btnEliminar);
                }
            }
        });
    }

    public void realizarPago() {
        generarPago();
        tvBuscarClientes1.getItems().removeAll(tvBuscarClientes1.getItems());
    }

    // Generar pagos a insertar
    public void generarPago() {

        // Variable para almacenar el total a pagar
        float totalPagar = 0;

        // Obtener los datos de la tabla
        ObservableList<DetalleFactura> detalleFacturasPagar = tvBuscarClientes1.getItems();

        Pago pago = new Pago();
        pago.setUsuario(usuario);
        pago.setFecha_pago(LocalDate.now());
        pago.setTotal_pagar(0f);

        IGenericService<Pago> pagoService = new GenericServiceImpl<>(Pago.class,
                HibernateUtil.getSessionFactory());
        pagoService.save(pago);

        Pago pagoId = pagoService.getById(pago.getId());

        for (DetalleFactura detalleFactura1 : detalleFacturasPagar) {

            totalPagar += detalleFactura1.getTotal_pagar();

            // Obtener el detalle factura
            IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                    HibernateUtil.getSessionFactory());
            DetalleFactura detalleFactura = detalleFacturaService.getById(detalleFactura1.getId());

            // Insertar en la tabla detalle_pago
            DetallePago detallePago = new DetallePago();
            detallePago.setPago(pagoId);
            detallePago.setDetalleFactura(detalleFactura);
            detallePago.setDescripcion("Pago de factura");

            IGenericService<DetallePago> detallePagoService = new GenericServiceImpl<>(DetallePago.class,
                    HibernateUtil.getSessionFactory());
            detallePagoService.save(detallePago);
        }

        // Actualizar el total a pagar
        pagoId.setTotal_pagar(totalPagar);
        pagoService.update(pagoId);

        // Actualizar el estado de la factura
        for (DetalleFactura detalleFactura1 : detalleFacturasPagar) {
            IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                    HibernateUtil.getSessionFactory());
            DetalleFactura detalleFactura = detalleFacturaService.getById(detalleFactura1.getId());
            detalleFactura.getFactura().setEstado("pagada");
            detalleFacturaService.update(detalleFactura);
        }


    }

    // Abrir ventana FacturaSecundaria

    public void mostrarSecundaria(ActionEvent actionEvent) {
        if (tvBuscarClientes1.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningun contrato");
            alert.setContentText("Por favor seleccione un contrato para facturar");
            alert.showAndWait();
        } else {
            try {
                ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FacturaSecundaria.fxml"));
                AnchorPane root = loader.load();

                FacturaSecundariaController facturaSecundariaController = loader.getController();
                facturaSecundariaController.recibirDatos(facturarController, tvBuscarClientes1.getItems());
                //mostrar el nombre del cliente en txtCliente
                facturaSecundariaController.txtCliente.setText(tvBuscarClientes1.getItems().get(0)
                        .getFactura().getCliente().getPrimer_nombre() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getSegundo_nombre() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getPrimer_apellido() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getSegundo_apellido());

                facturaSecundariaController.colN1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getServicio()
                        .getNombre()));
                facturaSecundariaController.colN2.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                        .getCliente().getPrimer_nombre()
                        + " " + cellData.getValue().getFactura().getCliente().getSegundo_nombre()
                        + " " + cellData.getValue().getFactura().getCliente().getPrimer_apellido()
                        + " " + cellData.getValue().getFactura().getCliente().getSegundo_apellido())
                );
                facturaSecundariaController.colN3.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                        .getFecha_factura().toString())
                );
                facturaSecundariaController.colN4.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFactura()
                        .getEstado())
                );
                facturaSecundariaController.colN5.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_pagar()
                        .toString())
                );

                Stage stage = new Stage();
                stage.setTitle("Facturar");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
