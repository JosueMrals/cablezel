package com.josue.view;

import com.josue.modelo.Barrio;
import com.josue.modelo.Cliente;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import com.josue.util.GlobalUtil;
import org.controlsfx.control.textfield.TextFields;

public class ClientesController implements Initializable {
    
    public TextField txtBuscarCedula;
    public TextField txtBuscarNombre;
    public Button btnRecargar;
    public Button btnBuscarNombre;
    public Button btnBuscarCedula;
    @FXML TextField txtNumCedula;
    @FXML TextField txtPrimerNombre;
    @FXML TextField txtSegundoNombre;
    @FXML TextField txtPrimerApellido;
    @FXML TextField txtSegundoApellido;
    @FXML TextArea txtDireccion;
    @FXML ComboBox<Barrio> cbBarrio;
    @FXML TextField txtNumTelefono;
    @FXML TableColumn<Cliente, String> colCedula;
    @FXML TableColumn<Cliente, String> colNombre;
    @FXML TableColumn<Cliente, String> colDireccion;
    @FXML TableColumn<Cliente, String> colBarrio;
    @FXML TableColumn<Cliente, String> colTelefono;
    @FXML TableView<Cliente> tvClientes;

    String[] clientesAutocomplete = {};
    String[] cedulaAutocomplete = {};

    /**
     * Initializes the controller class.
     * @author Josue
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crearClientes();
        listarBarrios();
        llenarClientes();
        autoCompletarNombre();
        autoCompletarCedula();
    }

    public ObservableList<Cliente> getClientes() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
    }

    public void crearClientes(){
        ObservableList<Cliente> clientes = getClientes();

        if (clientes.isEmpty()) {

            IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil
                    .getSessionFactory());
            Barrio barrio = barrioService.getId(1L);

            Cliente cliente = new Cliente();
            cliente.setNum_cedula("610-170101-1003M");
            cliente.setPrimer_nombre("Josue");
            cliente.setSegundo_nombre("Naun");
            cliente.setPrimer_apellido("Morales");
            cliente.setSegundo_apellido("Alvarez");
            cliente.setDireccion("Por la panaderia");
            cliente.setBarrio(barrio);
            cliente.setNum_telefono("5726-3856");

            IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil
                    .getSessionFactory());
            clienteService.save(cliente);
        }
    }

    public void autoCompletarNombre() {
            clientesAutocomplete = GlobalUtil.obtenerClientes();
            TextFields.bindAutoCompletion(txtBuscarNombre, clientesAutocomplete);
            llenarClientes();
            txtBuscarNombre.textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("oldValue " + oldValue);
                System.out.println("newValue " + newValue);
            });
            tvClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void autoCompletarCedula() {
        cedulaAutocomplete = GlobalUtil.obtenerCedula();
        TextFields.bindAutoCompletion(txtBuscarCedula, cedulaAutocomplete);
        llenarClientes();
        txtBuscarNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue " + oldValue);
            System.out.println("newValue " + newValue);
        });
        tvClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void listarBarrios(){
        var barrios = obtenerBarrios();
        cbBarrio.setValue(null);
        cbBarrio.setItems(barrios);
        cbBarrio.setPromptText("Seleccione un barrio");
    }

    public void llenarClientes() {
        ObservableList<Cliente> clientes = getClientes();
        colCedula.setCellValueFactory(new PropertyValueFactory<>("num_cedula"));
        colNombre.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrimer_nombre()
                        + " " + param.getValue().getSegundo_nombre() + " " +
                        param.getValue().getPrimer_apellido() + " " +
                        param.getValue().getSegundo_apellido())
        );
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colBarrio.setCellValueFactory(new PropertyValueFactory<>("barrio"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("num_telefono"));
        tvClientes.setItems(clientes);
    }

    public void registrarClientes() {

        //Validar que los campos no esten vacios
        if (txtNumCedula.getText().isEmpty() || txtPrimerNombre.getText().isEmpty() || txtPrimerApellido.
                getText().isEmpty() || txtDireccion.getText().isEmpty() || txtNumTelefono.getText().isEmpty()
                || cbBarrio.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar cliente");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();

        } else {
            // Validar que no se repita el numero de cedula
            IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.
                    getSessionFactory());
            ObservableList<Cliente> clientes = getClientes();
            for (Cliente cliente : clientes) {
                if (cliente.getNum_cedula().equals(txtNumCedula.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al registrar cliente");
                    alert.setContentText("El numero de cedula ya existe");
                    alert.showAndWait();
                    return;
                }
            }

            // Obtener los datos del formulario
            String numcedula = txtNumCedula.getText();
            String primernombre = txtPrimerNombre.getText();
            String segundonombre = txtSegundoNombre.getText();
            String primerapellido = txtPrimerApellido.getText();
            String segundo_apellido = txtSegundoApellido.getText();
            String direccion = txtDireccion.getText();
            Barrio barrio = cbBarrio.getValue();
            String numtelefono = txtNumTelefono.getText();

            // Crear el cliente
            try {
                Cliente cl = new Cliente();

                cl.setNum_cedula(numcedula);
                cl.setPrimer_nombre(primernombre);
                cl.setSegundo_nombre(segundonombre);
                cl.setPrimer_apellido(primerapellido);
                cl.setSegundo_apellido(segundo_apellido);
                cl.setDireccion(direccion);
                cl.setBarrio(barrio);
                cl.setNum_telefono(numtelefono);

                // Guardar el cliente
                clienteService.save(cl);

                // Limpiar el formulario
                txtNumCedula.clear();
                txtPrimerNombre.clear();
                txtSegundoNombre.clear();
                txtSegundoApellido.clear();
                txtPrimerApellido.clear();
                txtSegundoApellido.clear();
                txtDireccion.clear();
                cbBarrio.getSelectionModel().clearSelection();
                txtNumTelefono.clear();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El cliente se insertó correctamente.",
                        ButtonType.OK);
                alert.showAndWait();

                llenarClientes();


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void buscarCedula() {
        if (txtBuscarCedula.getText() != null) {
            llenarClientes();
        } else {
            ObservableList<Cliente> clientes = getClientes();
            ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
            for (Cliente cliente : clientes) {
                if (cliente.getNum_cedula().equals(txtBuscarCedula.getText())) {
                    clientesFiltrados.add(cliente);
                }
            }
            tvClientes.setItems(clientesFiltrados);
        }
    }

    public void buscarNombre() {
        if (txtBuscarNombre.getText() != null) {
            llenarClientes();
        } else {
            ObservableList<Cliente> clientes = getClientes();
            ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
            for (Cliente cliente : clientes) {
                if ((cliente.getPrimer_nombre() + " " + cliente.getSegundo_nombre()
                        + " " + cliente.getPrimer_apellido() + " " + cliente.getSegundo_apellido())
                        .equals(txtBuscarNombre.getText())) {
                    clientesFiltrados.add(cliente);
                }
            }
            tvClientes.setItems(clientesFiltrados);
        }
    }

    public ObservableList<Barrio> obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.
                getSessionFactory());
        return FXCollections.observableArrayList(barrioService.getAll());
    }

    public void recargar() {
        txtBuscarNombre.clear();
        txtBuscarCedula.clear();
        llenarClientes();
    }

}
