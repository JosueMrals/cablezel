package com.josue.view;

import com.josue.modelo.Barrio;
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
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Josue on 09/05/2021.
 * <p>
 *     Clase que controla la vista de clientes.
 * </p>
 */
public class ClientesController implements Initializable {
    @FXML
    TableView tvClientes;
    @FXML
    TextField txtNumCedula;
    @FXML
    TextField txtPrimerNombre;
    @FXML
    TextField txtSegundoNombre;
    @FXML
    TextField txtPrimerApellido;
    @FXML
    TextField txtSegundoApellido;
    @FXML
    TextArea txtDireccion;
    @FXML
    ComboBox<Barrio> cbBarrio;
    @FXML
    ComboBox<Contrato> cbTipoCliente;
    @FXML
    TextField txtNumTelefono;

    /**
     * Initializes the controller class.
     * @return void
     * @param url
     * @param resourceBundle
     * @throws Exception
     * @author Josue
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar el comboBox de barrios
        var barrios = obtenerBarrios();
        cbBarrio.setValue(null);
        cbBarrio.setItems(barrios);

        cbBarrio.setPromptText("Seleccione un barrio");

        // Inicializar el comboBox de tipos de contrato
        var tiposContrato = obtenerContrato();
        cbTipoCliente.setValue(null);
        cbTipoCliente.setItems(tiposContrato);
        cbTipoCliente.setPromptText("Seleccione un tipo de contrato");
    }

    /**
     * Obtiene todos los barrios de la base de datos
     * @return void
     * @throws Exception
     * @author Josue
     */
    public void registrarClientes(){
        // Obtener los datos del formulario
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());

        String numcedula = txtNumCedula.getText();
        String primernombre = txtPrimerNombre.getText();
        String segundonombre = txtSegundoNombre.getText();
        String primerapellido = txtPrimerApellido.getText();
        String segundo_apellido = txtSegundoApellido.getText();
        String direccion = txtDireccion.getText();
        Barrio barrio = cbBarrio.getValue();
        Contrato tipocontrato = cbTipoCliente.getValue();
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
            cl.setContrato(tipocontrato);
            cl.setNum_telefono(numtelefono);

            // Guardar el cliente
            clienteService.save(cl);

            // Limpiar el formulario
            txtNumCedula.clear();
            txtPrimerNombre.clear();
            txtSegundoApellido.clear();
            txtPrimerApellido.clear();
            txtSegundoApellido.clear();
            txtDireccion.clear();
            cbBarrio.getSelectionModel().clearSelection();
            cbTipoCliente.getSelectionModel().clearSelection();
            txtNumTelefono.clear();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El cliente se insertó correctamente." , ButtonType.OK);
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Obtiene los barrios de la base de datos
     * @return ObservableList<Barrio>
     * @throws Exception
     * @author Yesser
     */
    public ObservableList<Barrio> obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
        ObservableList<Barrio> barrios = FXCollections.observableArrayList(barrioService.getAll());

        return barrios;
    }

    /**
     * Obtiene los tipos de contrato de la base de datos
     * @return ObservableList<TipoContrato>
     * @throws Exception
     * @author Yesser
     */
    private ObservableList<Contrato> obtenerContrato() {
        var tiposContrato = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory())
                .getAll();
        return FXCollections.observableArrayList(tiposContrato);
    }


}
