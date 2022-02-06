package com.josue.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class MainInterfaceController {


    public void mostrar_clientes(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mostrando clientes" , ButtonType.OK);
        alert.showAndWait();
    }

    public void mostrar_contratos(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mostrando clientes" , ButtonType.OK);
        alert.showAndWait();
    }

    public void mostrar_facturar(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mostrando clientes" , ButtonType.OK);
        alert.showAndWait();
    }

    public void mostrar_gestiones(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Gestiones.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Gestionando");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mostrando clientes" , ButtonType.OK);
        alert.showAndWait();
    }
}
