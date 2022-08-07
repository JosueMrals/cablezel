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

/**
 * Created by Josue on 09/05/2021.
 * <p>
 *     Clase que controla la vista de clientes.
 * </p>
 */
public class ClientesController implements Initializable {
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

    /**
     * Initializes the controller class.
     * @author Josue
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarBarrios();
        llenarClientes();
    }

    public void listarBarrios(){
        // Inicializar el comboBox de barrios
        var barrios = obtenerBarrios();
        cbBarrio.setValue(null);
        cbBarrio.setItems(barrios);

        cbBarrio.setPromptText("Seleccione un barrio");
    }

    public void llenarClientes() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.
                getSessionFactory());
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteService.getAll());
        colCedula.setCellValueFactory(new PropertyValueFactory<>("num_cedula"));
        colNombre.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper(param.getValue().getPrimer_nombre()
                        + " " + param.getValue().getSegundo_nombre() + " " +
                        param.getValue().getPrimer_apellido() + " " + param.getValue().getSegundo_apellido())
        );
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colBarrio.setCellValueFactory(new PropertyValueFactory<>("barrio"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("num_telefono"));
        tvClientes.setItems(clientes);

    }

    public void registrarClientes(){

        //Validar que los campos no esten vacios
        if(txtNumCedula.getText().isEmpty() || txtPrimerNombre.getText().isEmpty() || txtPrimerApellido.
                getText().isEmpty() || txtDireccion.getText().isEmpty() || txtNumTelefono.getText().isEmpty()
                || cbBarrio.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar cliente");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
            return;
        }

        // Obtener los datos del formulario
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class,
                HibernateUtil.getSessionFactory());

        String numcedula = txtNumCedula.getText();
        String primernombre = txtPrimerNombre.getText();
        String segundonombre = txtSegundoNombre.getText();
        String primerapellido = txtPrimerApellido.getText();
        String segundo_apellido = txtSegundoApellido.getText();
        String direccion = txtDireccion.getText();
        Barrio barrio = cbBarrio.getValue();
        String numtelefono = txtNumTelefono.getText();

        // Crear el cliente
        try{
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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El cliente se insertó correctamente." ,
                    ButtonType.OK);
            alert.showAndWait();

            llenarClientes();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Obtiene los barrios de la base de datos
     * @return ObservableList<Barrio>
     * @author Yesser
     */
    public ObservableList<Barrio> obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.
                getSessionFactory());
        return FXCollections.observableArrayList(barrioService.getAll());
    }

}
