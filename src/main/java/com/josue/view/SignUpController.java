package com.josue.view;

import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    TextField txtNombres;
    @FXML
    TextField txtApellidos;
    @FXML
    TextField txtNick;
    @FXML
    TextField txtClave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void registrarUsuarios() {

        IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil.getSessionFactory());

        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String usuario = txtNick.getText();
        String clave = txtClave.getText();

        try {
            Usuario us = new Usuario();
            us.setNombres(nombres);
            us.setApellidos(apellidos);
            us.setNick(usuario);
            us.setClave(clave);

            //Guardar
            usuarioService.save(us);

            //GenericDao.getInstance().insertar(us);

            txtNombres.clear();
            txtApellidos.clear();
            txtNick.clear();
            txtApellidos.clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El usuario se inserto correctamente." , ButtonType.OK);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    public void cerrarRegistrarseMouseClick() {
        Platform.exit();
    }


}
