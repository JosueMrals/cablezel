
package com.josue.view;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.josue.modelo.Cliente;
import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * FXML Controller class
 *
 * @author Josh
 */
public class LoginController implements Initializable {

    public Pane leftPane;
    @FXML TextField txtNombreUsuario;
    @FXML PasswordField txtPassword;
    @FXML Button btnEntrar;

    Usuario usuario;
    ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    public LoginController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearUsuario();
        usuario = new Usuario();

        clientes = GlobalUtil.obtenerClientesObservableList();

    }

    public ObservableList<Usuario> obtenerUsuarios() {
        IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(usuarioService.getAll());
    }

    public boolean getUsuariobyNick(String nick, String password) {
        ObservableList<Usuario> usuarios = obtenerUsuarios();
        for (Usuario us : usuarios) {
            if (us.getNickusuario().equals(nick) && us.getPassword().equals(password)) {
                this.usuario = us;
                return true;
            }
        }
        return false;
    }

    public void crearUsuario() {
        ObservableList<Usuario> usuarios = obtenerUsuarios();
        if (usuarios.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombrecompleto("Josue Morales");
            usuario.setNickusuario("josue");
            usuario.setPassword("1234");
            usuario.setEmail("josue@gmail.com");

            IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil
                    .getSessionFactory());
            usuarioService.save(usuario);
        }
    }

    public void mostrarOtro(ActionEvent actionEvent) {

        if (txtNombreUsuario.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar sesion");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
        } else {

            String nick = txtNombreUsuario.getText();
            String password = txtPassword.getText();

            if (getUsuariobyNick(nick, password)) {
                ManejadorUsuario manejador = ManejadorUsuario.getInstance();
                manejador.setUsuario(usuario);
                try {
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PropuestasUsuarios.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al iniciar sesión");
                alert.setContentText("Usuario o contraseña incorrectos");
                alert.showAndWait();
            }
        }
    }


}
