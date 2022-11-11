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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

public class ContratoController implements Initializable {

    // Lector de registro de Log4j
    private static final Logger logger = LogManager.getLogger(ContratoController.class);

    @FXML DatePicker dpFechacontrato;
    @FXML TextArea txtDescripcion;
    @FXML ComboBox<TipoContrato> cbTipocontrato;
    @FXML TextField txtNombreCliente;
    @FXML TableColumn<Contrato, String> colFecha;
    @FXML TableColumn<Contrato, String> colDescripcion;
    @FXML TableColumn<Contrato, String> colTipo;
    @FXML TableColumn<Contrato, String> colCliente;
    @FXML TableView<Contrato> tvContratos;
    ObservableList<Cliente> listaClientes;
    Cliente clienteSeleccionado;
    Usuario usuario;
    Servicio servicioSeleccionado;
    @FXML TextField txtBuscarContrato;
    @FXML Button btnBuscarContrato;
    @FXML TableColumn<Contrato, String> colAccion;
    @FXML Button btnGuardar;

    Contrato contratoSeleccionado;

    // Autocompletar
    String[] contratosAutocomplete = {};
    String[] clientesAutocomplete = {};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarClientes();
        listarTipoContrato();
        llenarContrato();
        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        usuario = manejador.getUsuario();
        validarServicio();
        addButtonEdit();
        autoCompletarContrato();
    }

    private void autoCompletarContrato() {
        contratosAutocomplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarContrato, contratosAutocomplete);
        llenarContrato();
        tvContratos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void addButtonEdit() {
        colAccion.setCellFactory(new Callback<TableColumn<Contrato, String>, TableCell<Contrato, String>>() {
            @Override
            public TableCell<Contrato, String> call(TableColumn<Contrato, String> param) {
                final TableCell<Contrato, String> cell = new TableCell<Contrato, String>() {
                    final Button btnEditar = new Button();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lapiz.png")));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            btnEditar.setGraphic(imageView);
                            btnEditar.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                            btnEditar.setOnAction(event -> {
                                Contrato contrato = getTableView().getItems().get(getIndex());
                                txtNombreCliente.setText(contrato.getCliente().getPrimer_nombre() + " " +
                                        contrato.getCliente().getSegundo_nombre() + " " +
                                        contrato.getCliente().getPrimer_apellido() + " " +
                                        contrato.getCliente().getSegundo_apellido());
                                cbTipocontrato.setValue(contrato.getTipocontrato());
                                dpFechacontrato.setValue(contrato.getFecha_contrato());
                                txtDescripcion.setText(contrato.getDescripcion());
                                btnGuardar.setText("Actualizar");

                                btnGuardar.setOnAction(event1 -> {
                                    actualizarContrato(contrato);
                                    llenarContrato();
                                    recargarContrato();
                                });
                            });
                            setGraphic(btnEditar);
                            setText(null);
                        }
                    }

                    private void actualizarContrato(Contrato contrato) {
                        try {
                            IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class,
                                    HibernateUtil.getSessionFactory());

                            obtenerClienteSeleccionado();

                            contrato.setCliente(clienteSeleccionado);
                            contrato.setTipocontrato(cbTipocontrato.getValue());
                            contrato.setFecha_contrato(dpFechacontrato.getValue());
                            contrato.setDescripcion(txtDescripcion.getText());
                            contratoService.update(contrato);

                            txtNombreCliente.clear();
                            cbTipocontrato.setPromptText("Seleccione un tipo de contrato");
                            dpFechacontrato.setValue(LocalDate.now());
                            txtDescripcion.clear();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contrato actualizado correctamente", ButtonType.OK);
                            alert.showAndWait();

                            btnGuardar.setText("Guardar");
                            btnGuardar.setOnAction(event -> {
                                registrarContratos();
                                llenarContrato();
                                recargarContrato();
                            });
                        } catch (Exception e) {
                            logger.error("Error al actualizar contrato", e);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void recargarContrato() {
        txtBuscarContrato.clear();
        llenarContrato();
        tvContratos.refresh();
    }

    public void crearServicio(Long valor) {
        Servicio serv = new Servicio();
        serv.setNombre("CONTRATO");
        serv.setDescripcion("Servicio de contratos");
        serv.setPrecio(1080.0f);

        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class,
                HibernateUtil.getSessionFactory());
        servicioService.save(serv);
        servicioSeleccionado = servicioService.getById(valor);
    }

    private void validarServicio() {
        ObservableList<Servicio> servicios = GlobalUtil.getServicios();
        if(servicios.isEmpty()) {
            crearServicio(1l);
            System.out.println("Servicio creado: " + servicioSeleccionado);
        } else {
            servicioSeleccionado = obtenerServicioContrato();
            logger.info("Servicio obtenido: " + servicioSeleccionado);
            if(servicioSeleccionado == null) {
                // Resolver problema de servicio con autoincrement
                crearServicio(1l + servicios.size());
                logger.info("Servicio creado: " + servicioSeleccionado);
            }
        }
    }

    public void crearFactura() {
        Factura factura = new Factura();
        factura.setFecha_factura(LocalDate.now());
        factura.setTotal(servicioSeleccionado.getPrecio());
        factura.setUsuario(usuario);
        factura.setEstado("pendiente");
        factura.setCliente(clienteSeleccionado);
        IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class,
                HibernateUtil.getSessionFactory());
        facturaService.save(factura);

        //Obtener factura creada
        Factura facturaCreada = facturaService.getById(factura.getId());
        System.out.println("Factura creada: " + facturaCreada);

        //Crear detalle de factura
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setDescripcion(servicioSeleccionado.getDescripcion());
        detalleFactura.setFactura(facturaCreada);
        detalleFactura.setServicio(servicioSeleccionado);
        detalleFactura.setTotal_pagar(servicioSeleccionado.getPrecio());

        IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura.class,
                HibernateUtil.getSessionFactory());
        detalleFacturaService.save(detalleFactura);

        // obtener la cantidad de cantidad de tv de tipo de contrato
        int cantidad = Integer.parseInt(contratoSeleccionado.getTipocontrato().getCantidad_tv());

        // crear factura de tv
        Factura facturaTv = new Factura();
        facturaTv.setFecha_factura(LocalDate.now());
        facturaTv.setUsuario(usuario);
        facturaTv.setTotal(0.0f);
        facturaTv.setEstado("pendiente");
        facturaTv.setCliente(clienteSeleccionado);
        IGenericService<Factura> facturaTvService = new GenericServiceImpl<>(Factura.class,
                HibernateUtil.getSessionFactory());
        facturaTvService.save(facturaTv);

        //Obtener factura creada
        Factura facturaTvCreada = facturaTvService.getById(facturaTv.getId());
        System.out.println("Factura creada: " + facturaTvCreada);

        // total de factura TV
        float total = 0;

        //Crear facturas de servicios de tv instaladas
        for(int i = 0; i < cantidad; i++) {
            //Crear detalle de factura
            DetalleFactura detalleFacturaTv = new DetalleFactura();
            detalleFacturaTv.setDescripcion("Servicio de tv");
            detalleFacturaTv.setFactura(facturaTvCreada);
            detalleFacturaTv.setServicio(contratoSeleccionado.getTipocontrato().getServicio());
            detalleFacturaTv.setTotal_pagar(contratoSeleccionado.getTipocontrato().getServicio().getPrecio());

            total += contratoSeleccionado.getTipocontrato().getServicio().getPrecio();

            IGenericService<DetalleFactura> detalleFacturaTvService = new GenericServiceImpl<>(DetalleFactura.class,
                    HibernateUtil.getSessionFactory());
            detalleFacturaTvService.save(detalleFacturaTv);
        }

        // actualizar factura con el total de la factura
        facturaTvCreada.setTotal(total);
        facturaTvService.update(facturaTvCreada);

    }

    //Obtener el servicio de nombre contrato
    public Servicio obtenerServicioContrato() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil
                .getSessionFactory());
        String consulta = "select s from Servicio s where nombre = :nombre";
        Map<String, Object> parametros = Map.of("nombre", "contrato");
        List<Servicio> servicios = servicioService.query(consulta, parametros);
        if(servicios.size() > 0) {
            return servicios.get(0);
        }
        return null;

    }

    public void listarTipoContrato() {
        var tipocontratos = GlobalUtil.getTipoContratos();
        cbTipocontrato.setItems(tipocontratos);
        cbTipocontrato.setValue(null);
        cbTipocontrato.setPromptText("Seleccione un tipo de contrato");
    }

    public void listarClientes(){
        clientesAutocomplete = GlobalUtil.obtenerClientes();
        listaClientes = GlobalUtil.getClientes();
        TextFields.bindAutoCompletion(txtNombreCliente, clientesAutocomplete);
    }

    public void llenarContrato() {
        try {
            IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory());
            ObservableList<Contrato> listaContratos = FXCollections.observableArrayList(contratoService.getAll());
            if (listaContratos.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al llenar la tabla de contratos");
                alert.setContentText("Sin contratos registrados");
                alert.showAndWait();
                logger.error("Error al llenar la tabla de contratos", listaContratos);
                return;
            }
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_contrato"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colTipo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTipocontrato().getTipo_contrato()));
            colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getCliente().getSegundo_apellido()));
            tvContratos.setItems(listaContratos);

        } catch (Exception e) {
            logger.error("Error al llenar la tabla de contratos", e);
        }
    }

    public void registrarContratos() {

        if (dpFechacontrato.getValue() == null || txtDescripcion.getText().isEmpty() || cbTipocontrato.getValue()
                == null || txtNombreCliente.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar contrato");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
            return;
        }

        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class,
                HibernateUtil.getSessionFactory());

        var fecha_contrato = dpFechacontrato.getValue();
        String descripcion = txtDescripcion.getText();
        TipoContrato tipoContrato = cbTipocontrato.getValue();
        String nombreCliente = txtNombreCliente.getText();

        Cliente cliente = null;
        for (Cliente c : listaClientes) {
            if ((c.getPrimer_nombre() + " " + c.getSegundo_nombre() + " " + c.getPrimer_apellido() + " " +
                    c.getSegundo_apellido()).equals(nombreCliente)) {
                cliente = c;
                clienteSeleccionado = c;
            }
        }

        try {
            Contrato co = new Contrato();
            co.setFecha_contrato(fecha_contrato);
            co.setDescripcion(descripcion);
            co.setTipocontrato(tipoContrato);
            co.setCliente(cliente);

            contratoService.save(co);

            // obtener el contrato recien creado
            contratoSeleccionado = contratoService.getById(co.getId());

            dpFechacontrato.setValue(null);
            txtDescripcion.setText(null);
            cbTipocontrato.setValue(null);
            txtNombreCliente.setText(null);

            llenarContrato();
            crearFactura();
            crearFacturaAutomatica();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Contrato registrado correctamente",
                    ButtonType.OK);
            alert.show();
        }

    catch(Exception e)

        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al registrar el contrato" +
                    e.getMessage(), ButtonType.OK);
            alert.showAndWait();

        }

    }

    public void obtenerClienteSeleccionado() {
        String nombreCliente = txtNombreCliente.getText();
        for (Cliente c : listaClientes) {
            if ((c.getPrimer_nombre() + " " + c.getSegundo_nombre() + " " + c.getPrimer_apellido() + " " +
                    c.getSegundo_apellido()).equals(nombreCliente)) {
                clienteSeleccionado = c;
            }
        }
    }

    public void verificarTipoContrato(MouseEvent mouseEvent) {
        if (cbTipocontrato.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar contrato");
            alert.setContentText("Por favor, ingrese un tipo de contrato");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void buscarContrato(ActionEvent actionEvent) {
        String nombreCliente = txtBuscarContrato.getText();
        if (txtBuscarContrato.getText().isEmpty()) {
            llenarContrato();
        } else {
            ObservableList<Contrato> listaContratos = GlobalUtil.getContratos();
            ObservableList<Contrato> listaContratosFiltrada = FXCollections.observableArrayList();
            for (Contrato c : listaContratos) {
                if ((c.getCliente().getPrimer_nombre() + " " + c.getCliente().getSegundo_nombre() + " " +
                        c.getCliente().getPrimer_apellido() + " " + c.getCliente().getSegundo_apellido()).toLowerCase()
                        .contains(nombreCliente.toLowerCase())) {
                    listaContratosFiltrada.add(c);
                }
            }
            tvContratos.setItems(listaContratosFiltrada);
        }
    }

    // Crear factura automatica
    public void crearFacturaAutomatica() {
        IGenericService<FacturaAutomatica> facAutoService = new GenericServiceImpl<>(FacturaAutomatica.class,
                HibernateUtil.getSessionFactory());

        FacturaAutomatica facAuto = new FacturaAutomatica();
        facAuto.setContrato(contratoSeleccionado);
        facAuto.setCliente(clienteSeleccionado);
        facAuto.setFecha_factura_automatica(LocalDate.now().plusMonths(1));
        facAuto.setCantidad_facturas(0);

        facAutoService.save(facAuto);
    }
}

