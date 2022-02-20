
package com.josue.view;


import java.net.URL;
import java.util.List;
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
    @FXML TextField cjUser;
    @FXML PasswordField cjPassword;
    @FXML Button btnLogin;
    @FXML Label lblError;
    @FXML TableView<Usuario> table;
    @FXML TableColumn<Usuario, String> colNombre;
    @FXML TableColumn<Usuario, String> colApellidos;
    @FXML TableColumn<Usuario, String> colNick;
    private ObservableList<Usuario> usuarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IGenericService<Usuario> clienteService = new GenericServiceImpl<Usuario>(Usuario.class, HibernateUtil.getSessionFactory());

        //colNick.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nick"));
        usuarios = FXCollections.observableArrayList(clienteService.getAll());
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Apellidos"));
        table.setItems(usuarios);
    }

    @FXML
    void iniciarSesion(ActionEvent evt) {

        IGenericService<Usuario> usuarioService = new GenericServiceImpl<Usuario>(Usuario.class, HibernateUtil.getSessionFactory());
        try{
            Usuario us = new Usuario();
            us.setNombres("Yesser");
            us.setApellidos("Miranda");
            us.setNick("yesser97");
            us.setClave("12345678");

            //Guardar
            usuarioService.save(us);

            //GenericDao.getInstance().insertar(us);

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
