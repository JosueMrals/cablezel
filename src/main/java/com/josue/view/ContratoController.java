package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.Contrato;
import com.josue.modelo.TipoContrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

public class ContratoController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var tipoContratos = obtenerTipoContratos();
        cbTipocontrato.setValue(null);
        cbTipocontrato.setItems(tipoContratos);
        cbTipocontrato.setPromptText("Seleccione un tipo de contrato");
        clientesAutocomplete = GlobalUtil.obtenerClientes();
        listaClientes = obtenerClientesList();
        TextFields.bindAutoCompletion(txtNombreCliente,clientesAutocomplete);

        llenarContrato();
    }

    private ObservableList<Cliente> obtenerClientesList() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
    }

    public void llenarContrato() {
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory());
        ObservableList<Contrato> listaContratos = FXCollections.observableArrayList(contratoService.getAll());
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_contrato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTipo.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Contrato, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getTipocontrato().getTipo_contrato());
                    }
                }
        );
        colCliente.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Contrato, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getCliente().toString());
                    }
                }
        );
        tvContratos.setItems(listaContratos);
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

}

