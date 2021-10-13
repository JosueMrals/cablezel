
package com.josue.view;


import com.josue.dao.GenericDao;
import com.josue.modelo.Conexion;
import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.josue.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
        try{
            Usuario us = new Usuario();
            us.setId(1L);
            us.setNombres("Yesser");
            us.setApellidos("Miranda");
            us.setNick("yesser97");
            us.setClave("12345678");

            GenericDao.getInstance().insertar(us);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        /*Conexion con = new Conexion();
        
        try {
            ResultSet rs = con.CONSULTAR("SELECT * FROM usuarios WHERE usuario='" + cjUser.getText().trim() + "' AND contrasena='" + cjPassword.getText().trim() + "'; ");
            if (rs.next()) { 
                System.out.println("INICIADO");
            } else {
                lblError.setText("ERROR AL INICIAR");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
}
