package com.josue.view;

import com.josue.modelo.*;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.text.StyledEditorKit;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
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

    public float totalFactura = 0;

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
            + " " + detallesFactura.getFactura().getCliente().getSegundo_apellido()).contains(nombreCliente) && detallesFactura.getFactura().getEstado().equals("pendiente")) {
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
                        addFacturarButtonToTable();
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

        Contrato contratoCortado = new Contrato();

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

            // Comprobar si hay un servicio de tipo CORTE
            if (detalleFactura1.getServicio().getNombre().equals("CORTE")) {
                contratoCortado = detalleFactura1.getFactura().getContrato();
            }

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
        totalFactura = totalPagar;
        // Actualizar el total a pagar
        pagoId.setTotal_pagar(totalPagar);
        pagoService.update(pagoId);

        // Actualizar el estado de la factura
        ObservableList<Factura> facturasPagadas = FXCollections.observableArrayList();
        for (DetalleFactura detalleFactura1 : detalleFacturasPagar) {
            IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                    HibernateUtil.getSessionFactory());
            IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class,
                    HibernateUtil.getSessionFactory());
            DetalleFactura detalleFactura = detalleFacturaService.getById(detalleFactura1.getId());
            Factura factura = detalleFactura.getFactura();
            factura.setEstado("Pagada");

            // Actualizar el estado de la factura
            facturaService.update(factura);
            facturasPagadas.add(factura);

            // Actualizar el estado del detalle factura
            detalleFactura.setFactura(factura);
            detalleFacturaService.update(detalleFactura);
        }

        // Actualizar el estado del corte
        if (contratoCortado.getId() != null) {
            IGenericService<Corte> corteService = new GenericServiceImpl<>(Corte.class,
                    HibernateUtil.getSessionFactory());
            ObservableList<Corte> listaCortes = GlobalUtil.getCortes();

            for (Corte corte : listaCortes) {
                if (corte.getContrato().getId() == contratoCortado.getId()) {
                    corte.setEstado("inactivo");
                    corteService.update(corte);
                }
            }
        }

        // Actualizar fecha en la tabla FacturaAutomatica
        IGenericService<FacturaAutomatica> facturaAutomaticaService = new GenericServiceImpl<>(FacturaAutomatica.class,
                HibernateUtil.getSessionFactory());
        ObservableList<FacturaAutomatica> facturasAutomaticas = GlobalUtil.getFacturaAutomatica();
        LocalDate fechaUltimaFactura = obtenerUltimaFechaFacturasPagadas(facturasPagadas);
        for (Factura factura : facturasPagadas) {
            // Obtener el contrato de la factura
            Contrato contrato = factura.getContrato();
            for(FacturaAutomatica facturaAutomatica : facturasAutomaticas) {
                // Obtener el cliente de la factura automatica
                Contrato contrato1 = facturaAutomatica.getContrato();
                if (Objects.equals(contrato.getId(), contrato1.getId()) && facturaAutomatica.getCantidad_facturas() > 0) {
                    // Actualizar la fecha de la factura automatica
                    if (Objects.equals(contratoCortado.getId(), contrato.getId())) {
                        facturaAutomatica.setFecha_factura_automatica(LocalDate.now().plusMonths(1));
                    } else {
                        facturaAutomatica.setFecha_factura_automatica(fechaUltimaFactura.plusMonths(1));
                    }
                    facturaAutomatica.setCantidad_facturas(facturaAutomatica.getCantidad_facturas() - 1);
                    facturaAutomaticaService.update(facturaAutomatica);
                }
            }
        }
    }

    public LocalDate obtenerUltimaFechaFacturasPagadas(ObservableList<Factura> facturasPagadas ) {
        // Comparación entre todas las fecha y obtener la última
        LocalDate fechaUltimaFactura = null;
        for (Factura factura : facturasPagadas) {
            if (fechaUltimaFactura == null) {
                fechaUltimaFactura = factura.getFecha_factura();
            } else {
                if (factura.getFecha_factura().isAfter(fechaUltimaFactura)) {
                    fechaUltimaFactura = factura.getFecha_factura();
                }
            }
        }
        return fechaUltimaFactura;
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

                ObservableList<DetalleFactura> datos = tvBuscarClientes1.getItems();
                String clientePagado = tvBuscarClientes1.getItems().get(0)
                        .getFactura().getCliente().getPrimer_nombre() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getSegundo_nombre() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getPrimer_apellido() + " " +
                        tvBuscarClientes1.getItems().get(0).getFactura().getCliente().getSegundo_apellido();

                // obtener fecha de factura
                String fechaFactura = tvBuscarClientes1.getItems().get(0).getFactura().getFecha_factura().toString();

                // Obtener el total a pagar
                FacturaSecundariaController facturaSecundariaController = loader.getController();
                //mostrar el nombre del cliente en txtCliente
                facturaSecundariaController.txtCliente.setText(clientePagado);
                //mostrar la fecha de la factura en txtFecha
                facturaSecundariaController.txtFechaFactura.setText(fechaFactura);

                // establecer id de factura
                String idFactura = String.valueOf(tvBuscarClientes1.getItems().get(0).getFactura().getId());

                // Obtener todos los id de Factura
                ObservableList<String> idFacturas = FXCollections.observableArrayList();
                for (DetalleFactura detalleFactura : datos) {
                    if (!idFacturas.contains(String.valueOf(detalleFactura.getFactura().getId()))) {
                        idFacturas.add(String.valueOf(detalleFactura.getFactura().getId()));
                    }
                }

                facturaSecundariaController.txtNumFactura.setText(idFacturas.stream().reduce((a, b) -> a + ", " + b).get().toString());

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

                facturaSecundariaController.recibirDatos(facturarController, datos);

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
