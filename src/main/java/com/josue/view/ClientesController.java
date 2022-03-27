package com.josue.view;

import com.josue.modelo.Cliente;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

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
    ComboBox cbBarrio;
    @FXML
    ComboBox cbTipoCliente;
    @FXML
    TextField txtNumTelefono;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void registrarClientes(){
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());

        String numcedula = txtNumCedula.getText();
        String primernombre = txtPrimerNombre.getText();
        String segundonombre = txtSegundoNombre.getText();
        String primerapellido = txtPrimerApellido.getText();
        String segundo_apellido = txtSegundoApellido.getText();
        String direccion = txtDireccion.getText();
        String barrio = cbBarrio.getValue().toString();
        String tipocliente = cbTipoCliente.getValue().toString();
        String numtelefono = txtNumTelefono.getText();

        try{
            Cliente cl = new Cliente();
            cl.setNum_cedula(numcedula);
            cl.setPrimer_nombre(primernombre);
            cl.setSegundo_nombre(segundonombre);
            cl.setPrimer_apellido(primerapellido);
            cl.setSegundo_apellido(segundo_apellido);
            cl.setDireccion(direccion);
            //cl.setCod_barrio(barrio);
            //cl.setId_tipo_cliente(tipocliente);
            cl.setNum_telefono(numtelefono);

            clienteService.save(cl);

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

}
