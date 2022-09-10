package com.josue.view;

import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class UsuariosController implements Initializable {

    @FXML
    TextField txtNombreCompleto;
    @FXML
    TextField txtNombreUsuarios;
    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void registarUsuario() {
        //Validar que los campos no esten vacios
        if (txtNombreCompleto.getText().isEmpty() || txtNombreUsuarios.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar usuario");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
            return;
        } else {

            //Validar que los datos no se repitan
            IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil.
                    getSessionFactory());

            if (usuarioService.getAll().stream().anyMatch(u -> u.getNombrecompleto().equals(txtNombreCompleto.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al registrar usuario");
                alert.setContentText("El nombre completo ya existe");
                alert.showAndWait();
                return;
            } else {

                //Obtener los datos del formulario
                String nombrecompleto = txtNombreCompleto.getText();
                String nombreusuario = txtNombreUsuarios.getText();
                String email = txtEmail.getText();
                String password = txtPassword.getText();

                //Crear un nuevo usuario
                try {
                    Usuario usuario = new Usuario();
                    usuario.setNombrecompleto(nombrecompleto);
                    usuario.setNickusuario(nombreusuario);
                    usuario.setEmail(email);
                    usuario.setPassword(password);

                    //Guardar el usuario en la base de datos
                    usuarioService.save(usuario);

                    //Limpiar el formulario
                    txtNombreCompleto.setText("");
                    txtNombreUsuarios.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Info: El usuario se insertó correctamente.",
                            ButtonType.OK);
                    alert.showAndWait();

                    registarUsuario();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(),
                            ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }

    }
}
