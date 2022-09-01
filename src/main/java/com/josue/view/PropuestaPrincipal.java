package com.josue.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropuestaPrincipal extends Application implements Initializable {
    static final Logger logger = LogManager.getLogger(PropuestaPrincipal.class);

    public BorderPane panelPadre;
    public Button btnServicios;
    public Button btPrincipalConfig;
    public Button btPrincipalServicios;
    public Button btPrincipalContratos;
    public Button btPrincipalFacturar;
    public Button btPrincipalClientes;
    @FXML Button btnConfig;
    @FXML Button btnReportes;
    @FXML Button btnInicio;
    @FXML Button btnClientes;
    @FXML Button btnCli;
    @FXML Button btnContratos;
    @FXML Button btnUsuarios;
    @FXML Button btnFacturar;
    @FXML Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void start(Stage stage) {

    }

    public void mostrar_clientes() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_facturar() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FacturarPrincipal.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    public void mostrar_gestiones() {
        try {
            FXMLLoader Loader = new FXMLLoader(getClass().getResource("/fxml/Configuraciones.fxml"));
            Pane configuraciones = Loader.load();
            panelPadre.setCenter(configuraciones);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_usuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegistrarUsuarios.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_contratos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Contratos.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_servicios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Servicio.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_reportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reportes.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
