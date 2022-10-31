package com.josue.view;

import com.josue.modelo.ConfiguracionSistema;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.persistence.criteria.Predicate;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfiguracionSistemaController implements Initializable {
    public TextField txtServidor;
    public TextField txtRespaldo;
    public TextField txtPuerto;
    public TextField txtHost;
    public ImageView ivAbrirDirectorioServidor;
    public TextField txtUsuario;
    public TextField txtClave;
    public TextField txtBaseDatos;
    public Button btConfigurar;
    public Button btCancelar;
    public ImageView ivAbrirDirectorioFichero;

    private Boolean activado = false;

    ManejadorUsuario manejadorUsuario;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manejadorUsuario = ManejadorUsuario.getInstance();
        if(llenarTextFields()){
            ivAbrirDirectorioServidor.setDisable(true);
            ivAbrirDirectorioFichero.setDisable(true);
            manejadorUsuario.setServidorConfigurado(true);
            activado = true;
        } else {
            btConfigurar.setDisable(false);
            btCancelar.setDisable(false);
            manejadorUsuario.setServidorConfigurado(false);
            activado = false;
        }

    }

    private boolean llenarTextFields() {
        IGenericService<ConfiguracionSistema> service = new GenericServiceImpl<>(ConfiguracionSistema.class, HibernateUtil.getSessionFactory());
        List<ConfiguracionSistema> configuracionSistemas = service.getAll();
        if (configuracionSistemas.size() < 1) {
            return false;
        }
        configuracionSistemas.forEach(configuracionSistema -> {
            switch (configuracionSistema.getNombre()) {
                case "servidor":
                    txtServidor.setText(configuracionSistema.getValor());
                    txtServidor.setEditable(false);
                    break;
                case "puerto":
                    txtPuerto.setText(configuracionSistema.getValor());
                    txtPuerto.setEditable(false);
                    break;
                case "host":
                    txtHost.setText(configuracionSistema.getValor());
                    txtHost.setEditable(false);
                    break;
                case "usuario":
                    txtUsuario.setText(configuracionSistema.getValor());
                    txtUsuario.setEditable(false);
                    break;
                case "clave":
                    txtClave.setText(configuracionSistema.getValor());
                    txtClave.setEditable(false);
                    break;
                case "baseDatos":
                    txtBaseDatos.setText(configuracionSistema.getValor());
                    txtBaseDatos.setEditable(false);
                    break;
                case "respaldo":
                    txtRespaldo.setText(configuracionSistema.getValor());
                    txtRespaldo.setEditable(false);
                    break;
            }
        });
        return true;

    }

    public void abrirDirectorio(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar directorio");
        // obtener el stage
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File directorioSeleccionado = directoryChooser.showDialog(stage);

        if (directorioSeleccionado != null) {
            System.out.println("Directorio seleccionado: " + directorioSeleccionado.getAbsolutePath());
            txtRespaldo.setText(directorioSeleccionado.getAbsolutePath());
        }
    }

    public void btConfigurarClick(ActionEvent actionEvent) {
        // validar campos vacios
        if (txtServidor.getText().isEmpty() || txtRespaldo.getText().isEmpty() || txtPuerto.getText().isEmpty() ||
                txtHost.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtClave.getText().isEmpty() ||
                txtBaseDatos.getText().isEmpty()) {
            // mostrar una alerta al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al configurar el sistema");
            alert.setContentText("Todos los campos son obligatorios");
            alert.showAndWait();
            return;
        } else {

            // obtener todos los datos ingresados
            String servidor = txtServidor.getText();
            String respaldo = txtRespaldo.getText();
            String puerto = txtPuerto.getText();
            String host = txtHost.getText();
            String usuario = txtUsuario.getText();
            String clave = txtClave.getText();
            String baseDatos = txtBaseDatos.getText();

            // guardar en el archivo de configuracion
            IGenericService<ConfiguracionSistema> configuracionSistemaService = new GenericServiceImpl<>(ConfiguracionSistema.class, HibernateUtil.getSessionFactory());

            // crear un objeto de tipo ConfiguracionSistema
            List<ConfiguracionSistema> configuracionSistemas = new ArrayList<>();
            configuracionSistemas.add(new ConfiguracionSistema("servidor", servidor));
            configuracionSistemas.add(new ConfiguracionSistema("respaldo", respaldo));
            configuracionSistemas.add(new ConfiguracionSistema("puerto", puerto));
            configuracionSistemas.add(new ConfiguracionSistema("host", host));
            configuracionSistemas.add(new ConfiguracionSistema("usuario", usuario));
            configuracionSistemas.add(new ConfiguracionSistema("clave", clave));
            configuracionSistemas.add(new ConfiguracionSistema("baseDatos", baseDatos));

            if (!activado){
                // guardar en la base de datos
                configuracionSistemas.forEach(configuracionSistemaService::save);
                manejadorUsuario.setServidorConfigurado(true);
            } else {
                configuracionSistemas.forEach(configuracionSistemaService::update);
            }
        }

    }

    public void btCancelarClick(ActionEvent actionEvent) {
        // cerrar esta ventana
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void abrirDirectorioFichero(MouseEvent mouseEvent) {
        // obtener el ejecutable del servidor
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar ejecutable del servidor");
        // obtener el stage
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File ficheroSeleccionado = fileChooser.showOpenDialog(stage);

        if (ficheroSeleccionado != null) {
            System.out.println("Fichero seleccionado: " + ficheroSeleccionado.getAbsolutePath());
            txtServidor.setText(ficheroSeleccionado.getAbsolutePath());
        }
    }

    public void activarEdicion(MouseEvent mouseEvent) {
        if (activado) {
            // activar la edicion de los campos
            txtServidor.setEditable(true);
            txtPuerto.setEditable(true);
            txtHost.setEditable(true);
            txtUsuario.setEditable(true);
            txtClave.setEditable(true);
            txtBaseDatos.setEditable(true);
            txtRespaldo.setEditable(true);
            btConfigurar.setDisable(false);
            btCancelar.setDisable(false);
            activado = false;

            // alertar al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Información");
            alert.setContentText("Ahora puede editar los campos");
            alert.showAndWait();
        } else {
            // desactivar la edicion de los campos
            txtServidor.setEditable(false);
            txtPuerto.setEditable(false);
            txtHost.setEditable(false);
            txtUsuario.setEditable(false);
            txtClave.setEditable(false);
            txtBaseDatos.setEditable(false);
            txtRespaldo.setEditable(false);
            btConfigurar.setDisable(true);
            btCancelar.setDisable(true);
            activado = true;

            // alertar al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Información");
            alert.setContentText("Ahora no puede editar los campos");
            alert.showAndWait();
        }
    }
}

