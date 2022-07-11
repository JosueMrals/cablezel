package com.josue.cablezelmnv;

import java.io.IOException;
import java.util.List;
import com.josue.modelo.Barrio;
import com.josue.modelo.Cliente;
import com.josue.modelo.TipoContrato;
import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Inicio de Sesion");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        IGenericService<Usuario> clienteService = new GenericServiceImpl<>(Usuario.class, HibernateUtil.getSessionFactory());

        //Show All Usuarios
        List<Usuario> clienteUsuario = clienteService.getAll();
        if (clienteUsuario != null) {
            for (Usuario c : clienteUsuario) {
                System.out.println("Nombre Completo: " + c.getNombrecompleto());
                System.out.println("Nombre de Usuario: " + c.getNickusuario());
                System.out.println("------------------------------------");
            }
        }

        //Show All Barrios
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
        List<Barrio> clienteBarrio = barrioService.getAll();
        if (clienteBarrio != null) {
            for (Barrio b : clienteBarrio) {
                System.out.println("Nombre barrio: " + b.getNombre_barrio());
                System.out.println("Descripcion del barrio: " + b.getDescripcion());
                System.out.println("------------------------------------------------------");
            }
        }

        //Show All TiposContratos
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.getSessionFactory());
        List<TipoContrato> clienteTipoContrato = tpContratoService.getAll();
        if (clienteTipoContrato != null) {
            for (TipoContrato tc : clienteTipoContrato) {
                System.out.println("Codigo del Contrato: " + tc.getCod_tipocontrato());
                System.out.println("Tipo del Contrato: " + tc.getTipo_contrato());
                System.out.println("Cantidad de Televisores: " + tc.getCantidad_tv());
                System.out.println("Precio del Contrato: " + tc.getPrecio_contrato());
                System.out.println("Descripcion del barrio: " + tc.getDescripcion());
                System.out.println("--------------------------------------------------------");
            }
        }

        //Show all Clientes
        IGenericService<Cliente> Service = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        List<Cliente> clienteCliente = Service.getAll();
        if (clienteCliente != null) {
            for (Cliente cli : clienteCliente) {
                System.out.println("Numero de cedula: " + cli.getNum_cedula());
                System.out.println("Primer Nombre: " + cli.getPrimer_nombre());
                System.out.println("Segundo Nombre: " + cli.getSegundo_nombre());
                System.out.println("Primer Apellido: " + cli.getPrimer_apellido());
                System.out.println("Segundo Apellido: " + cli.getSegundo_apellido());
                System.out.println("Direccion: " + cli.getDireccion());
                System.out.println("Numero de telefono: " + cli.getNum_telefono());
                System.out.println("--------------------------------------------------------");
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
