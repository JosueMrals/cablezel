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
    public void initialize(URL location, ResourceBundle resources) { // Inicializar el comboBox de tipo contrato
        var tipoContratos = obtenerTipoContratos(); //obtener los tipos de contrato de la base de datos
        cbTipocontrato.setValue(null); //Seleccionar nulo por defecto
        cbTipocontrato.setItems(tipoContratos); //Setear los tipos de contrato en el comboBox
        cbTipocontrato.setPromptText("Seleccione un tipo de contrato"); //Seleccione un tipo de contrato
        clientesAutocomplete = GlobalUtil.obtenerClientes(); //obtener los clientes de la base de datos
        listaClientes = obtenerClientesList(); //obtener los clientes de la base de datos
        TextFields.bindAutoCompletion(txtNombreCliente,clientesAutocomplete); //AutoCompletar el campo de nombre de cliente

        llenarContrato(); //Llenar la tabla de contratos
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

    public void registrarContratos() { //Registrar contrato

        // Validar que los campos no esten vacios
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
                HibernateUtil.getSessionFactory()); //Crear el objeto contrato

        var fecha_contrato = dpFechacontrato.getValue(); //Fecha de contrato
        String descripcion = txtDescripcion.getText(); //Descripcion del contrato
        TipoContrato tipoContrato = cbTipocontrato.getValue(); //Tipo de contrato
        String nombreCliente = txtNombreCliente.getText(); //Nombre del cliente

        Cliente cliente = null; //Crear un cliente nulo
        for (Cliente c : listaClientes) { //Recorrer la lista de clientes
            if ((c.getPrimer_nombre() + " " + c.getSegundo_nombre() + " " + c.getPrimer_apellido() + " " +
                    c.getSegundo_apellido()).equals(nombreCliente)) {
                cliente = c;
            }
        }

        try {
            Contrato co = new Contrato(); //Crear un nuevo contrato
            co.setFecha_contrato(fecha_contrato); //Asignar la fecha de contrato
            co.setDescripcion(descripcion); //Asignar la descripcion del contrato
            co.setTipocontrato(tipoContrato); //Asignar el tipo de contrato
            co.setCliente(cliente); //Asignar el nombre del cliente

            contratoService.save(co); //Guardar el contrato

            //Limpiar el formulario
            dpFechacontrato.setValue(null); //Fecha de contrato
            txtDescripcion.setText(null); //Descripcion del contrato
            cbTipocontrato.setValue(null);  //Tipo de contrato
            txtNombreCliente.setText(null); //Nombre del cliente

            llenarContrato();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Contrato registrado correctamente",
                    ButtonType.OK); //Crear una alerta de confirmación
            alert.show(); //Mostrar el mensaje de confirmación

        }

    catch(Exception e)

        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al registrar el contrato" +
                    e.getMessage(), ButtonType.OK); //Crear una alerta de confirmación
            alert.showAndWait(); //Mostrar el mensaje de confirmación

        }

    }

    /**
     * Obtiene los tipos de contrato de la base de datos
     * @return ObservableList<TipoContrato>
     * @throws Exception
     * @author Josue
     */

    public ObservableList<TipoContrato> obtenerTipoContratos() { //Obtener los tipos de contrato de la base de datos
        IGenericService<TipoContrato> tipocontratosService = new GenericServiceImpl<>(TipoContrato.class,
                HibernateUtil.getSessionFactory()); //Crear una instancia de la clase GenericServiceImpl
        return FXCollections.observableArrayList(tipocontratosService.getAll());
    }

}

