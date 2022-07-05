package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.modelo.Contrato;
import com.josue.modelo.TipoContrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
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
    // se crea variable con el mismo nombre que el FXML
    @FXML TableView<Contrato> tvContratos;

    String[] clientesAutocomplete = {};

    ObservableList<Cliente> listaClientes;
    String nombreCliente;



    @Override
    public void initialize(URL location, ResourceBundle resources) { // Inicializar el comboBox de tipo contrato
        var tipoContratos = obtenerTipoContratos(); //obtener los tipos de contrato de la base de datos
        cbTipocontrato.setValue(null); //Seleccionar nulo por defecto
        cbTipocontrato.setItems(tipoContratos); //Setear los tipos de contrato en el comboBox
        cbTipocontrato.setPromptText("Seleccione un tipo de contrato"); //Seleccione un tipo de contrato
        clientesAutocomplete = obtenerClientes(); //obtener los clientes de la base de datos
        TextFields.bindAutoCompletion(txtNombreCliente,clientesAutocomplete); //AutoCompletar el campo de nombre de cliente

        llenarContrato(); //Llenar la tabla de contratos
    }

    public void llenarContrato() {
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory());
        ObservableList<Contrato> listaContratos = FXCollections.observableArrayList(contratoService.getAll());
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_contrato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipocontrato_id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente_id"));
        tvContratos.setItems(listaContratos);
    }

    private String[] obtenerClientes() {
        var clientes = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory()).getAll(); //obtener los clientes de la base de datos
        var listaC = FXCollections.observableArrayList(clientes); //convertir la lista de clientes a un observableList
        listaClientes = listaC; //guardar la lista de clientes en una variable global
        var clientesAutocomplete = new String[clientes.size()]; //crear un arreglo de string con el tamaño de los clientes
        for (int i = 0; i < clientes.size(); i++) { //recorrer el arreglo de clientes
            clientesAutocomplete[i] = clientes.get(i).getPrimer_nombre() + " " + clientes.get(i).getSegundo_nombre()
            + " " +  clientes.get(i).getPrimer_apellido() + " " +  clientes.get(i).getSegundo_apellido(); //agregar el nombre de cada cliente al arreglo de string
        }
        return clientesAutocomplete; //retornar el arreglo de string con los nombres de los clientes
    }

    public void registrarContratos() { //Registrar contrato
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory()); //Crear el objeto contrato

        var fecha_contrato = dpFechacontrato.getValue(); //Fecha de contrato
        String descripcion = txtDescripcion.getText(); //Descripcion del contrato
        TipoContrato tipoContrato = cbTipocontrato.getValue(); //Tipo de contrato
        String nombreCliente = txtNombreCliente.getText(); //Nombre del cliente

        Cliente cliente = null; //Crear un cliente nulo
        for (Cliente c : listaClientes) { //Recorrer la lista de clientes
            if ((c.getPrimer_nombre() + " " + c.getSegundo_nombre() + " " + c.getPrimer_apellido() + " " + c.getSegundo_apellido()).equals(nombreCliente)) {
                cliente = c; //Si el nombre del cliente es igual al nombre del cliente de la lista de clientes, guardar el cliente en una variable global
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

            llenarContrato();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Contrato registrado correctamente", ButtonType.OK); //Crear una alerta de confirmación
            alert.show(); //Mostrar el mensaje de confirmación

        }

    catch(Exception e)

        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error al registrar el contrato" + e.getMessage(), ButtonType.OK); //Crear una alerta de confirmación
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
        IGenericService<TipoContrato> tipocontratosService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.getSessionFactory()); //Crear una instancia de la clase GenericServiceImpl
        return FXCollections.observableArrayList(tipocontratosService.getAll());
    }

}

