package com.josue.cablezelmnv;

import java.io.IOException;
import java.lang.System.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Josh
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         
        try {
            FXMLLoader loader = new FXMLLoader();
            GridPane padre = loader.load(getClass().getResourceAsStream("/fxml/SignUp.fxml"));
            // AnchorPane root = loader.load(getClass().getResourceAsStream("/fxml/SignUp.fxml"));
            Scene scene = new Scene(padre);
            primaryStage.setTitle("Hello Word");
            primaryStage.setScene(scene);
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
