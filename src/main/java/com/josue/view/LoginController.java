
package com.josue.view;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Josh
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField txtNombreUsuario;
    @FXML PasswordField txtPassword;
    @FXML Button btnEntrar;
    @FXML TableView<Usuario> table;
    @FXML TableColumn<Usuario, String> colNombre;
    @FXML TableColumn<Usuario, String> colApellidos;
    private ObservableList<Usuario> usuarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void iniciarSesion(ActionEvent evt) {

        IGenericService<Usuario> usuarioService = new GenericServiceImpl<Usuario>(Usuario.class, HibernateUtil.getSessionFactory());
        try{
            Usuario us = new Usuario();
            us.setNombrecompleto("Victor Zeledon");
            us.setNickusuario("poxs44");
            us.setPassword("poxs44");
            us.setEmail("vzeledon7@gmail.com");

            //Guardar
            usuarioService.save(us);

        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    public void mostrarRegistrar(ActionEvent actionEvent) {


    }
}
