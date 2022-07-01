package com.josue.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PropuestaPrincipal extends Application implements Initializable {
    public BorderPane panelPadre;
    @FXML Button btnConfig;
    @FXML Button btnReportes;
    @FXML Button btnInicio;
    @FXML Button btnClientes;
    @FXML Button btnContratos;
    @FXML Button btnUsuarios;
    @FXML Button btnFacturar;
    @FXML Button btnSalir;


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

    /*public void cambiarStyle(Object o)
    {
        Button btnConfig  = (Button) o;
        btnConfig.setFont(new Font("System Regular", 16.0));
    }

    public void quitarStyle(Object o)
    {
        Label etiqueta = (Label) o;
        etiqueta.setFont(new Font("System Regular", 16.0));
    }*/

    public void mostrar_clientes(MouseEvent mouseEvent) {
        //cambiarStyle(lbClientes);
        //quitarStyle(lbContratos);
        //quitarStyle(lbDashboard);
        //quitarStyle(lbGestiones);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_facturar(MouseEvent mouseEvent) {
        //cambiarStyle(lbClientes);
        //quitarStyle(lbContratos);
        //quitarStyle(lbDashboard);
        //quitarStyle(lbGestiones);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Facturar.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mostrar_gestiones(MouseEvent mouseEvent) {
        //cambiarStyle(lbGestiones);
        //quitarStyle(lbContratos);
        //quitarStyle(lbDashboard);
        //quitarStyle(lbClientes);
        try {
            FXMLLoader Loader = new FXMLLoader(getClass().getResource("/fxml/Configuraciones.fxml"));
            Pane configuraciones = Loader.load();
            panelPadre.setCenter(configuraciones);

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

    public void editar_tipocontratos(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editar/EditarTipoContrato.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
