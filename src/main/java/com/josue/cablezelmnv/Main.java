package com.josue.cablezelmnv;

import java.io.IOException;

import com.josue.modelo.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Josh
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Inicio de Sesion");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
