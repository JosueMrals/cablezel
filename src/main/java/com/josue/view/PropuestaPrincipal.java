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
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import org.kordamp.desktoppanefx.scene.layout.InternalWindow;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PropuestaPrincipal extends Application implements Initializable {
    @FXML Label lbDashboard;
    @FXML Label lbContratos;
    @FXML Label lbClientes;
    @FXML Label lbGestiones;
    private static int count = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void start(Stage stage) throws Exception {

        DesktopPane desktopPane = new DesktopPane();
        Button newWindow = new Button("New Window");
        newWindow.setOnAction(e -> {
            InternalWindow window = new InternalWindow(
                    "window-" + count,
                    new FontIcon("mdi-application:20"),
                    "Title " + count++,
                    new Label("Content"));
            desktopPane.addInternalWindow(window);
        });

        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(800, 600);
        mainPane.setTop(newWindow);
        mainPane.setCenter(desktopPane);

        stage.setScene(new Scene(mainPane));
        stage.show();
    }

    public void mostrar_contratos(MouseEvent mouseEvent) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PropuestaPrincipal.fxml"));
            Pane root = loader.load();

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
    }


}
