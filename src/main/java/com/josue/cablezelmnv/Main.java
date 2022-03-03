package com.josue.cablezelmnv;

import com.josue.modelo.Barrio;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Josh
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PropuestaPrincipal.fxml"));
            FXMLLoader cargadorclientes = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));

            BorderPane root = loader.load();
            Pane clientes = cargadorclientes.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Interfaz Principal");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //IGenericService<Usuario> clienteService = new GenericServiceImpl<>(Usuario.class, HibernateUtil.getSessionFactory());
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());

        //Show All
        List<Barrio> clienteUsuario = barrioService.getAll();

        if (clienteUsuario != null) {
            for (Barrio c : clienteUsuario) {
                System.out.println("Nombre del barrio: " + c.getNombre_barrio());
                System.out.println("Descripcion: " + c.getDescripcion());
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
