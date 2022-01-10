package com.josue.view;


import com.josue.dao.GenericDao;
import com.josue.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML TextField txtNombres;
    @FXML TextField txtApellidos;
    @FXML TextField txtNick;
    @FXML TextField txtClave;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void registrarUsuarios(ActionEvent actionEvent) {
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String usuario = txtNick.getText();
        String clave = txtClave.getText();

        try {
            Usuario us = new Usuario();
            us.setId(1L);
            us.setNombres(nombres);
            us.setApellidos(apellidos);
            us.setNick(usuario);
            us.setClave(clave);

            GenericDao.getInstance().insertar(us);

            txtNombres.clear();
            txtApellidos.clear();
            txtNick.clear();
            txtApellidos.clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El usuario se inserto correctamente " , ButtonType.OK);
            alert.showAndWait();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }



    }
}
