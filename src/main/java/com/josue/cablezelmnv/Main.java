package com.josue.cablezelmnv;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.util.List;

import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
/**
 *
 * @author Josh
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         
        try {
            //FXMLLoader loader = new FXMLLoader();
           // GridPane padre = loader.load(getClass().getResourceAsStream("/fxml/SignUp.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PropuestaPrincipal.fxml"));
            FXMLLoader cargadorclientes = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));

            BorderPane root = loader.load();
            Pane clientes = cargadorclientes.load();

            //ATENTO root.setCenter(clientes);

            Scene scene = new Scene(root);
            primaryStage.setTitle("Interfaz Principal");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        IGenericService<Usuario> clienteService = new GenericServiceImpl<Usuario>(Usuario.class, HibernateUtil.getSessionFactory());

        //Show All
        List<Usuario> clienteUsuario = clienteService.getAll();
        if (clienteUsuario != null) {
            for (Usuario c : clienteUsuario) {
                System.out.println("Nombre: " + c.getNombres().toString());
                System.out.println("Apellidos: " + c.getApellidos().toString());
                System.out.println("Nombre de Usuario: " + c.getNick().toString());
                System.out.println("----------------------------------");
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
