
package com.josue.view;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Josh
 */
public class LoginController implements Initializable {

    public Pane leftPane;
    /**
     * Initializes the controller class.
     */
    @FXML TextField txtNombreUsuario;
    @FXML PasswordField txtPassword;
    @FXML Button btnEntrar;

    public LoginController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void mostrarOtro(ActionEvent actionEvent) {
        try {
            // ocultar la ventana actual
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            //Cargar el archivo fxml y crear un nuevo stage para mostrar el formulario principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PropuestasUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
