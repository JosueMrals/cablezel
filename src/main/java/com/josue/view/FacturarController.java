package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.Factura;
import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.ResourceBundle;

public class FacturarController implements Initializable {
    public TextField txtBuscarCliente;
    public Button btnBuscar;
    public TableView<Factura> tvBuscarFacturas;
    public TableColumn<Factura, String> colUsuario;
    public TableColumn<Factura, String> colCliente;
    public TableColumn<Factura, String> colFecha;
    public TableColumn<Factura, String> colEstado;
    public TableColumn<Factura, String> colTotal;
    public TableColumn<Factura, String> colAccion;
    public Button btFacturar;
    public TableView<Factura> tvFacturasPagar;
    public TableColumn<Factura, String> colUsuario1;
    public TableColumn<Factura, String> colCliente1;
    public TableColumn<Factura, String> colFecha1;
    public TableColumn<Factura, String> colEstado1;
    public TableColumn<Factura, String> colTotal1;
    public TableColumn<Factura, String> colAccion1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarFacturas();

    }

    public void llenarFacturas() {
        IGenericService<Factura> service = new GenericServiceImpl<>(Factura.class,
                HibernateUtil.getSessionFactory());
        ObservableList<Factura> facturas = FXCollections.observableArrayList(service.getAll());
        colUsuario.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getUsuario().getNickusuario()));
        colCliente.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCliente().getPrimer_nombre()
                + " " + param.getValue().getCliente().getPrimer_apellido()
                + " " + param.getValue().getCliente().getSegundo_apellido()
                + " " + param.getValue().getCliente().getSegundo_nombre()));
        colFecha.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getFecha_factura().toString()));
        colEstado.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getEstado()));
        colTotal.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getTotal().toString()));
        tvBuscarFacturas.setItems(facturas);
    }

    public ObservableList<Factura> obtenerFacturasObservableList() {
        IGenericService<Factura> service = new GenericServiceImpl<>(Factura.class,
                HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(service.getAll());
    }

    public void crearFactura() {
        ObservableList<Factura> facturas = obtenerFacturasObservableList();
        if (facturas.isEmpty()) {
            Factura factura = new Factura();
            factura.setFecha_factura(LocalDateTime.now());
            factura.setEstado("PENDIENTE");
            IGenericService<Factura> service = new GenericServiceImpl<>(Factura.class,
                    HibernateUtil.getSessionFactory());
            service.update(factura);
            llenarFacturas();
        }

    }

}
