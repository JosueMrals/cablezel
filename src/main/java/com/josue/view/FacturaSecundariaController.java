package com.josue.view;

import com.josue.modelo.DetalleFactura;
import com.josue.modelo.Usuario;
import com.josue.reportes.Reportes;
import com.josue.util.ManejadorUsuario;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FacturaSecundariaController implements Initializable {

     // logger de Log4j
    private static final Logger logger = LogManager.getLogger(FacturaSecundariaController.class);

    public TableView<DetalleFactura> tvMostrarPagos;
    public TableColumn<DetalleFactura, String> colN1;
    public TableColumn<DetalleFactura, String> colN2;
    public TableColumn<DetalleFactura, String > colN3;
    public TableColumn<DetalleFactura, String > colN4;
    public TableColumn<DetalleFactura, String > colN5;
    public Button btFacturar;

    public Button btImprimir;
    public TextArea txtDireccion;
    public TextField txtCliente;
    public TextField txtNumFactura;
    public TextField txtFechaFactura;
    public TextField txtNota;
    public Label lbTotal;
    public Label lbDescuento;
    public Label lbSubTotal;
    FacturarController facturarController;

    public HashMap<String, Object> parametrosFactura = new HashMap<>();

    float total;

    Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // extraer el usuario de ManejadorUsuario
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
        usuario = manejadorUsuario.getUsuario();
    }

    public void recibirDatos(FacturarController facturarPrincipalController , ObservableList<DetalleFactura> datos){
        logger.info("Recibiendo datos de la factura principal");
        logger.info("Datos recibidos: " + datos);
        tvMostrarPagos.setItems(datos);
        this.facturarController = facturarPrincipalController;

        // obtener el total de la factura desde datos
        for (DetalleFactura detalleFactura : datos) {
            total += detalleFactura.getTotal_pagar();
        }


        lbTotal.setText(String.valueOf(total));
        lbDescuento.setText(String.valueOf(0.0));
        lbSubTotal.setText(String.valueOf(total ));

    }


    public void btCompletarClick(ActionEvent actionEvent) {
        // pagar
        facturarController.realizarPago();

        // alertar de pago realizado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pago realizado");
        alert.setHeaderText("Pago realizado");
        alert.setContentText("El pago se ha realizado correctamente");
        alert.showAndWait();

        // cerrar la ventana actual
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void btImprimirClick(ActionEvent actionEvent) throws Exception {
        // HashMap para los parametros del reporte
        HashMap<String, Object> parametros = new HashMap<>();

        // obtener los datos de los detalles de la factura
        ObservableList<DetalleFactura> detalles = tvMostrarPagos.getItems();

        // obtener el cliente
        String cliente = txtCliente.getText();
        String direccion = txtDireccion.getText();

        // obtener la factura
        String factura = txtNumFactura.getText();

        // obtener la fecha
        String fecha = txtFechaFactura.getText();

        // obtener la imagen
        URL urlLogo = FacturaSecundariaController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        BufferedImage urlImage = null;

        try {
            urlImage= ImageIO.read(urlLogo);
        } catch (Exception e) {
            logger.error("Error al obtener la imagen del logo", e);
        }

        // agregar los parametros
        parametros.put("logo", urlImage);

        // establecer los parametros
        parametros.put("cliente", cliente);
        parametros.put("direccion", direccion);
        parametros.put("factura", factura);
        parametros.put("fecha", fecha);
        parametros.put("usuario", usuario.getNickusuario());
        parametros.put("detalles", detalles);
        parametros.put("total", total);
        parametros.put("descuento", 0);
        parametros.put("subtotal", total);

        // establecer los parametros de la factura
        parametrosFactura = parametros;

        // imprimir el reporte
        Reportes.generarReporte("reportes/Factura.jrxml", parametrosFactura, new JRBeanCollectionDataSource(detalles));

    }
}
