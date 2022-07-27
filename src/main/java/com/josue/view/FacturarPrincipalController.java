package com.josue.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacturarPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarSecundaria(ActionEvent actionEvent) {
        try {

            // No ocultar la ventana actual
            ((Node)actionEvent.getSource()).getScene().getWindow();
            // Cargar el archivo fxml y crear un nuevo stage para mostrar el formulario principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FacturaDiseño.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Visualización de la factura");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
