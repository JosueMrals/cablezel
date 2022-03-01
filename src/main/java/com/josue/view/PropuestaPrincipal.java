package com.josue.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PropuestaPrincipal extends Application implements Initializable {
    public BorderPane panelPadre;
    @FXML Label lbDashboard;
    @FXML Label lbContratos;
    @FXML Label lbClientes;
    @FXML Label lbGestiones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void mostrar_contratos(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cambiarStyle(Object o)
    {
        Label etiqueta = (Label) o;
        etiqueta.setFont(new Font("System Regular", 16.0));

    }

    public void quitarStyle(Object o)
    {
        Label etiqueta = (Label) o;
        etiqueta.setFont(new Font("System Regular", 16.0));
    }

    public void mostrar_clientes(MouseEvent mouseEvent) {
        cambiarStyle(lbClientes);
        quitarStyle(lbContratos);
        quitarStyle(lbDashboard);
        quitarStyle(lbGestiones);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_facturar(MouseEvent mouseEvent) {

    }

    public void mostrar_gestiones(MouseEvent mouseEvent) {
        cambiarStyle(lbGestiones);
        quitarStyle(lbContratos);
        quitarStyle(lbDashboard);
        quitarStyle(lbClientes);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Configuraciones.fxml"));
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
    }


    public void mostrar_usuarios(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
