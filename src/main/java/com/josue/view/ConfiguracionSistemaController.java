package com.josue.view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfiguracionSistemaController implements Initializable {
    public TextField txtServidor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void abrirDirectorio(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar directorio");
        // obtener el stage
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File directorioSeleccionado = directoryChooser.showDialog(stage);

        if (directorioSeleccionado != null) {
            System.out.println("Directorio seleccionado: " + directorioSeleccionado.getAbsolutePath());
            txtServidor.setText(directorioSeleccionado.getAbsolutePath());
        }
    }

    public void btConfigurarClick(ActionEvent actionEvent) {
    }

    public void btCancelarClick(ActionEvent actionEvent) {
    }

}
