
package com.josue.view;


import com.josue.modelo.Conexion;
import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    void iniciarSesion(ActionEvent evt) {
        Conexion con = new Conexion();
        
        try {
            ResultSet rs = con.CONSULTAR("SELECT * FROM usuarios WHERE usuario='" + cjUser.getText().trim() + "' AND contrasena='" + cjPassword.getText().trim() + "'; ");
            if (rs.next()) { 
                System.out.println("INICIADO");
            } else {
                lblError.setText("ERROR AL INICIAR");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
