package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.DetalleFactura;
import com.josue.modelo.DetallePago;
import com.josue.modelo.Pago;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.security.GuardedObject;

public class PagoController implements Initializable {
    // Lector de registro de Log4j
    private static final Logger logger = LogManager.getLogger(PagoController.class);
    public TableView<DetallePago> tvPagos;
    public TableColumn<Pago, String> colId;
    public TableColumn<DetalleFactura, String> colCliente;
    public TableColumn<DetalleFactura, String> colServicio;
    public TableColumn<Pago, String> colUsuario;
    public TableColumn<Pago, String> colFecha;
    public TableColumn<Pago, String> colTotal;
    public TextField txtBuscarCliente;
    public TextField txtBuscarServicio;
    public TextField txtBuscarUsuario;
    public Button btImprimirPagos;
    public Button btBuscarCliente;
    public Button btBuscarServicio;
    public Button btBuscarUsuario;

    // Autocompletar
    String[] clientesAutoComplete = {};
    String[] serviciosAutoComplete = {};
    String[] usuariosAutoComplete = {};

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        llenarPagos();
        autocompletarCliente();
        autocompletarServicio();
        autocompletarUsuario();
    }

    @FXML
    public void buscarCliente() {
        String cliente = txtBuscarCliente.getText();
        if (cliente.isEmpty()) {
            llenarPagos();
        } else {
            ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
            ObservableList<DetallePago> clientesFiltrados = FXCollections.observableArrayList();
            for (DetallePago dp : detallePagos) {
                if ((dp.getDetalleFactura().getFactura().getCliente().getPrimer_nombre()
                + " " + dp.getDetalleFactura().getFactura().getCliente().getSegundo_nombre()
                + " " + dp.getDetalleFactura().getFactura().getCliente().getPrimer_apellido()
                + " " + dp.getDetalleFactura().getFactura().getCliente().getSegundo_apellido())
                        .toLowerCase().contains(cliente.toLowerCase())) {
                    clientesFiltrados.add(dp);
                }
            }
            tvPagos.setItems(clientesFiltrados);
        }
    }

    @FXML
    public void buscarServicio() {
        String servicio = txtBuscarServicio.getText();
        if (servicio.isEmpty()) {
            llenarPagos();
        } else {
            ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
            ObservableList<DetallePago> serviciosFiltrados = FXCollections.observableArrayList();
            for (DetallePago dp : detallePagos) {
                if (dp.getDetalleFactura().getServicio().getNombre().toLowerCase().contains(servicio.toLowerCase())) {
                    serviciosFiltrados.add(dp);
                }
            }
            tvPagos.setItems(serviciosFiltrados);
        }
    }

    @FXML
    public void buscarUsuario() {
        String usuario = txtBuscarUsuario.getText();
        if (usuario.isEmpty()) {
            llenarPagos();
        } else {
            ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
            ObservableList<DetallePago> usuariosFiltrados = FXCollections.observableArrayList();
            for (DetallePago dp : detallePagos) {
                if (dp.getPago().getUsuario().getNickusuario().toLowerCase().contains(usuario.toLowerCase())) {
                    usuariosFiltrados.add(dp);
                }
            }
            tvPagos.setItems(usuariosFiltrados);
        }
    }

    private void autocompletarUsuario() {
        usuariosAutoComplete = GlobalUtil.obtenerUsuarios();
        TextFields.bindAutoCompletion(txtBuscarUsuario, usuariosAutoComplete);
        tvPagos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autocompletarServicio() {
        serviciosAutoComplete = GlobalUtil.obtenerServicios();
        TextFields.bindAutoCompletion(txtBuscarServicio, serviciosAutoComplete);
        tvPagos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    private void autocompletarCliente() {
        clientesAutoComplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutoComplete);
        tvPagos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void llenarPagos() {
        try {
            IGenericService<DetallePago> pagoService = new GenericServiceImpl<>(DetallePago.class, HibernateUtil
                    .getSessionFactory());
            ObservableList<DetallePago> pagos = FXCollections.observableArrayList(pagoService.getAll());
            colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getId().toString()));
            colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFactura().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getFactura().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getFactura().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getFactura().getCliente().getSegundo_apellido()));
            colServicio.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getServicio().getNombre()));
            colUsuario.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getUsuario().getNickusuario()));
            colFecha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFecha_pago().toString()));
            colTotal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getTotal_pagar().toString()));
            tvPagos.setItems(pagos);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de pagos", e);
        }
    }


    public void imprimirPagos(ActionEvent actionEvent) {

    }
}
