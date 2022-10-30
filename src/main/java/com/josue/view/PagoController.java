package com.josue.view;

import com.josue.modelo.DetalleFactura;
import com.josue.modelo.DetallePago;
import com.josue.modelo.Pago;
import com.josue.reportes.Reportes;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;


public class PagoController implements Initializable {
    // Lector de registro de Log4j
    private static final Logger logger = LogManager.getLogger(PagoController.class);
    public TableView<DetallePago> tvPagos;
    public TableColumn<DetallePago, String> colId;
    public TableColumn<DetallePago, String> colCliente;
    public TableColumn<DetallePago, String> colServicio;
    public TableColumn<DetallePago, String> colUsuario;
    public TableColumn<DetallePago, String> colFecha;
    public TableColumn<DetallePago, String> colTotal;
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
        llenarPagosDiarios();
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
            colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getFactura().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getSegundo_apellido()));
            colServicio.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getServicio().getNombre()));
            colUsuario.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getPago().getUsuario().getNickusuario()));
            colFecha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getPago().getFecha_pago().toString()));
            colTotal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getTotal_pagar().toString()));
            tvPagos.setItems(pagos);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de pagos", e);
        }
    }

    public void imprimirPagos() throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();

        IGenericService<Pago> pagoService = new GenericServiceImpl<>(Pago.class, HibernateUtil
                .getSessionFactory());
        ObservableList<Pago> pagos = FXCollections.observableArrayList(pagoService.getAll());
        logger.info("Pagos: " + pagos);

        URL urlLogo = ReportesController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        BufferedImage urlImage = ImageIO.read(urlLogo);

        parametros.put("logo", urlImage);

        Reportes.generarReporte("reportes/Pagos.jrxml", parametros, new JRBeanCollectionDataSource(pagos));
    }

    public void llenarPagosDiarios() {
        ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
        ObservableList<DetallePago> pagosDiarios = FXCollections.observableArrayList();
        for (DetallePago dp : detallePagos) {
            if (dp.getPago().getFecha_pago().equals(LocalDate.now())) {
                pagosDiarios.add(dp);
            }
        }
        try {
            colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getId().toString()));
            colCliente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getFactura().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getDetalleFactura().getFactura().getCliente().getSegundo_apellido()));
            colServicio.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getServicio().getNombre()));
            colUsuario.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getPago().getUsuario().getNickusuario()));
            colFecha.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getPago().getFecha_pago().toString()));
            colTotal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getDetalleFactura().getTotal_pagar().toString()));
            tvPagos.setItems(pagosDiarios);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de pagos", e);
        }
    }

    public void imprimirPagosDiarios() throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();

        ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
        ObservableList<Pago> pagosDiarios = FXCollections.observableArrayList();
        for (DetallePago dp : detallePagos) {
            if (dp.getPago().getFecha_pago().equals(LocalDate.now())) {
                pagosDiarios.add(dp.getPago());
            }
        }
        logger.info("Pagos: " + pagosDiarios);

        URL urlLogo = ReportesController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        URL olas = ReportesController.class.getClassLoader().getResource( "reportes/waves.jpg") ;
        BufferedImage urlImage = null;
        BufferedImage urlImage2 = null;
        try {
            urlImage = ImageIO.read(urlLogo);
            urlImage2 = ImageIO.read(olas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parametros.put("logo", urlImage);
        parametros.put("olas", urlImage2);

        Reportes.generarReporte("reportes/Diario.jrxml", parametros, new JRBeanCollectionDataSource(pagosDiarios));
    }

    public void imprimirPagosMensual() throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();

        ObservableList<DetallePago> detallePagos = GlobalUtil.getDetallePago();
        ObservableList<Pago> pagosMensuales = FXCollections.observableArrayList();
        for (DetallePago dp : detallePagos) {
            if (dp.getPago().getFecha_pago().getMonth().equals(LocalDate.now().getMonth())) {
                pagosMensuales.add(dp.getPago());
            }
        }
        logger.info("Pagos: " + pagosMensuales);

        URL urlLogo = ReportesController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        URL olas = ReportesController.class.getClassLoader().getResource( "reportes/waves.jpg") ;
        BufferedImage urlImage = null;
        BufferedImage urlImage2 = null;
        try {
            urlImage = ImageIO.read(urlLogo);
            urlImage2 = ImageIO.read(olas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parametros.put("logo", urlImage);
        parametros.put("olas", urlImage2);

        Reportes.generarReporte("reportes/Mensual.jrxml", parametros, new JRBeanCollectionDataSource(pagosMensuales));
    }
}
