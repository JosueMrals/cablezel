package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.DetalleFactura;
import com.josue.modelo.Factura;
import com.josue.reportes.Reportes;
import com.josue.util.GlobalUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ClientesAvisarController implements Initializable {
    // logger of Log4j
    private static final Logger logger = LogManager.getLogger(ReportesController.class);

    public TableView<DetalleFactura> tvClientesAvisar;
    public TableColumn<DetalleFactura, String> colClienteId;
    public TableColumn<DetalleFactura, String> colClienteAvisar;
    public TableColumn<DetalleFactura, String> colServicioAvisar;
    public TableColumn<DetalleFactura, String> colUsuarioAvisar;
    public TableColumn<DetalleFactura, String> colFechaAvisar;
    public TableColumn<DetalleFactura, String> colTotalAvisar;
    public TextField txtBuscarCliente;
    public TextField txtBuscarServicio;
    public TextField txtBuscarUsuario;

    // Autocompletar
    String[] clientesAutoComplete = {};
    String[] serviciosAutoComplete = {};
    String[] usuariosAutoComplete = {};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autocompletarCliente();
        autocompletarServicio();
        autocompletarUsuario();
        llenarClientesAvisar();
    }
    @FXML
    public void buscarCliente() {
        String cliente = txtBuscarCliente.getText();
        if (cliente.isEmpty()) {
            llenarClientesAvisar();
        } else {
            ObservableList<DetalleFactura> lista = FXCollections.observableArrayList();
            for (DetalleFactura detalleFactura : GlobalUtil.getDetalleFactura()) {
                if ((detalleFactura.getFactura().getCliente().getPrimer_nombre().toLowerCase() + " " +
                        detalleFactura.getFactura().getCliente().getSegundo_nombre() + " " +
                        detalleFactura.getFactura().getCliente().getPrimer_apellido() + " " +
                        detalleFactura.getFactura().getCliente().getSegundo_apellido()).contains(cliente.toLowerCase())) {
                    lista.add(detalleFactura);
                }
            }
        }
    }

    @FXML
    public void buscarServicio() {
        String servicio = txtBuscarServicio.getText();
        if (servicio.isEmpty()) {
            llenarClientesAvisar();
        } else {
            ObservableList<DetalleFactura> detalleFacturas = GlobalUtil.getDetalleFactura();
            ObservableList<DetalleFactura> serviciosFiltrados = FXCollections.observableArrayList();
            for (DetalleFactura df : detalleFacturas) {
                if (df.getServicio().getNombre().toLowerCase().contains(servicio.toLowerCase())) {
                    serviciosFiltrados.add(df);
                }
            }
            tvClientesAvisar.setItems(serviciosFiltrados);
        }
    }

    @FXML
    public void buscarUsuario() {
        String usuario = txtBuscarUsuario.getText();
        if (usuario.isEmpty()) {
            llenarClientesAvisar();
        } else {
            ObservableList<DetalleFactura> detalleFacturas = GlobalUtil.getDetalleFactura();
            ObservableList<DetalleFactura> usuariosFiltrados = FXCollections.observableArrayList();
            for (DetalleFactura df : detalleFacturas) {
                if (df.getFactura().getUsuario().getNickusuario().toLowerCase().contains(usuario.toLowerCase())) {
                    usuariosFiltrados.add(df);
                }
            }
            tvClientesAvisar.setItems(usuariosFiltrados);
        }
    }

    private void autocompletarUsuario() {
        usuariosAutoComplete = GlobalUtil.obtenerUsuarios();
        TextFields.bindAutoCompletion(txtBuscarUsuario, usuariosAutoComplete);
        tvClientesAvisar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autocompletarServicio() {
        serviciosAutoComplete = GlobalUtil.obtenerServicios();
        TextFields.bindAutoCompletion(txtBuscarServicio, serviciosAutoComplete);
        tvClientesAvisar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autocompletarCliente() {
        clientesAutoComplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutoComplete);
        tvClientesAvisar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void llenarClientesAvisar() {

        ObservableList<Factura> f = FXCollections.observableArrayList();

        ObservableList<DetalleFactura> detalleFacturas = GlobalUtil.getDetalleFactura();
        ObservableList<Cliente> clientesAvisar = FXCollections.observableArrayList();
        for (DetalleFactura df : detalleFacturas) {
            if (df.getFactura().getEstado().equals("pendiente") && df.getServicio().getNombre().equals("BASICO")) {
                clientesAvisar.add(df.getFactura().getCliente());
                f.add(df.getFactura());
            }
        }

        ObservableList<Factura> facturasDosClientes = FXCollections.observableArrayList();
        for (Factura factura : f) {
            Integer total = 0;
            for (Cliente c : clientesAvisar) {
                if (factura.getCliente().equals(c)) {
                    total++;
                }
            }
            if (total ==2 ) {
                facturasDosClientes.add(factura);
            }
        }

        // filtrar clientes con 2 facturas pendientes
        ObservableList<DetalleFactura> detalleFacturas2 = FXCollections.observableArrayList();
        for (DetalleFactura df : detalleFacturas) {
            for (Factura factura : facturasDosClientes) {
                if (df.getFactura().equals(factura)) {
                    detalleFacturas2.add(df);
                }
            }
        }

        try {
            colClienteId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFactura().getCliente().getId().toString()));
            colClienteAvisar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFactura().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getFactura().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getFactura().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getFactura().getCliente().getSegundo_apellido()));
            colServicioAvisar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getServicio().getNombre()));
            colUsuarioAvisar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFactura().getUsuario().getNickusuario()));
            colFechaAvisar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFactura().getFecha_factura().toString()));
            colTotalAvisar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getTotal_pagar().toString()));
            tvClientesAvisar.setItems(detalleFacturas2);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de pagos", e);
        }
    }

    public void imprimirClientesAvisar() throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();

        ObservableList<Factura> facturas = GlobalUtil.getFacturas();
        ObservableList<Cliente> clientesAvisar = FXCollections.observableArrayList();
        for (Factura f : facturas) {
            if (f.getFecha_factura().plusMonths(2).equals(LocalDate.now())) {
                clientesAvisar.add(f.getCliente());
            }

        }
        logger.info("Clientes: " + clientesAvisar);

        URL urlLogo = ReportesController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        BufferedImage urlImage = null;
        try {
            urlImage = ImageIO.read(urlLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parametros.put("Logo", urlImage);

        Reportes.generarReporte("reportes/ClientesAvisar.jrxml", parametros, new JRBeanCollectionDataSource(clientesAvisar));

    }

}
