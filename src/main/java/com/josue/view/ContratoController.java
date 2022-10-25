package com.josue.view;

import com.josue.modelo.*;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
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

    String[] clientesAutocomplete = {};

    ObservableList<Cliente> listaClientes;

    Cliente clienteSeleccionado;
    Usuario usuario;
    Servicio servicioSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarClientes();
        listarTipoContrato();
        llenarContrato();
        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        usuario = manejador.getUsuario();
        validarServicio();
    }

    public void crearServicio(Long valor) {
        Servicio serv = new Servicio();
        serv.setNombre("contrato");
        serv.setDescripcion("Servicio de contratos");
        serv.setPrecio(700.0f);

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
        var tipocontratos = obtenerTipoContratos();
        cbTipocontrato.setItems(tipocontratos);
        cbTipocontrato.setValue(null);
        cbTipocontrato.setPromptText("Seleccione un tipo de contrato");
    }

    public void listarClientes(){
        clientesAutocomplete = GlobalUtil.obtenerClientes();
        listaClientes = obtenerClientesList();
        TextFields.bindAutoCompletion(txtNombreCliente, clientesAutocomplete);
    }

    public ObservableList<Contrato> obtenerContratos() {
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(contratoService.getAll());
    }

    private ObservableList<Cliente> obtenerClientesList() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
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
            colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCliente()
                    .getPrimer_nombre() + " " + cellData.getValue().getCliente().getSegundo_nombre()
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

            dpFechacontrato.setValue(null);
            txtDescripcion.setText(null);
            cbTipocontrato.setValue(null);
            txtNombreCliente.setText(null);

            llenarContrato();
            crearFactura();
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

    public ObservableList<TipoContrato> obtenerTipoContratos() {
        IGenericService<TipoContrato> tipocontratosService = new GenericServiceImpl<>(TipoContrato.class,
                HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(tipocontratosService.getAll());
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
}

