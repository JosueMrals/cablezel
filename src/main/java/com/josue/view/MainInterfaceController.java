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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mostrando clientes" , ButtonType.OK);
        alert.showAndWait();
    }
}
