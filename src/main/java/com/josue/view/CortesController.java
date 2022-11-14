package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.Corte;
import com.josue.modelo.DetalleFactura;
import com.josue.modelo.Factura;
import com.josue.reportes.Reportes;
import com.josue.service.IGenericService;
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
import java.util.HashMap;
import java.util.ResourceBundle;

public class CortesController implements Initializable {
    // logger of Log4j
    private static final Logger logger = LogManager.getLogger(ReportesController.class);

    public TableView<Corte> tvCortes;
    public TableColumn<Corte, String> colClorteId;
    public TableColumn<Corte, String> colClienteCorte;
    public TableColumn<Corte, String> colEstadoCorte;
    public TableColumn<Corte, String> colContratoCorte;
    public TableColumn<Corte, String> colFechaCorte;

    public TextField txtBuscarCliente;

    // Autocompletar
    String[] clientesAutoComplete = {};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autocompletarCliente();
        llenarCortes();
    }

    @FXML
    public void buscarCliente() {
        String cliente = txtBuscarCliente.getText();
        if (cliente.isEmpty()) {
            llenarCortes();
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

    private void autocompletarCliente() {
        clientesAutoComplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutoComplete);
        tvCortes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void llenarCortes() {
        ObservableList<Corte> lista = GlobalUtil.getCortes();
        try {
            colClorteId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getId().toString()));
            colClienteCorte.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getCliente().getPrimer_nombre()
                    + " " + cellData.getValue().getCliente().getSegundo_nombre()
                    + " " + cellData.getValue().getCliente().getPrimer_apellido()
                    + " " + cellData.getValue().getCliente().getSegundo_apellido()));
            colEstadoCorte.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getEstado()));
            colContratoCorte.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getContrato().getDescripcion()));
            colFechaCorte.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>( cellData.getValue().getFecha_corte().toString()));
            tvCortes.setItems(lista);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de cortes", e);
        }
    }

    public void imprimirCortes() throws Exception {
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

        Reportes.generarReporte("reportes/Cortes.jrxml", parametros, new JRBeanCollectionDataSource(clientesAvisar));

    }

}
