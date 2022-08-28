package com.josue.view;

import com.josue.modelo.Servicio;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ServicioController implements Initializable {

    @FXML TextField txtNombre;
    @FXML TextField txtDescripcion;
    @FXML TextField txtPrecio;
    @FXML  Button btnGuardar;
    @FXML TableView<Servicio> tvServicios;
    @FXML TableColumn<Servicio, String> colNombre;
    @FXML TableColumn<Servicio, String> colDescripcion;
    @FXML TableColumn<Servicio, String> colPrecio;

    ObservableList<Servicio> listaServicios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarServicio();

    }

    public void llenarServicio() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
        listaServicios = FXCollections.observableArrayList(servicioService.getAll());
        tvServicios.setItems(listaServicios);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    public void guardarServicio() {
        //Validar que los campos no esten vacios
        if(txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() ||
            txtPrecio.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al guardar el servicio");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
        } else {
            //Validar que no se repita el tipo de servicio
            IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class,
                    HibernateUtil.getSessionFactory());
            ObservableList<Servicio> servicios = FXCollections.observableArrayList(servicioService.getAll());
            for(Servicio servicio : servicios) {
                // Validar que no se repita el tipo de servicio
                if(servicio.getNombre().equals(txtNombre.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al guardar el servicio");
                    alert.setContentText("El servicio ya existe");
                    alert.showAndWait();
                    return;
                }
            }
            try {
                //Guardar el servicio
                Servicio sv = new Servicio();
                sv.setNombre(txtNombre.getText());
                sv.setDescripcion(txtDescripcion.getText());
                sv.setPrecio(Float.valueOf(txtPrecio.getText()));

                servicioService.save(sv);

                //Limpiar los campos
                txtNombre.setText("");
                txtDescripcion.setText("");
                txtPrecio.setText("");

                //Actualizar la tabla
                llenarServicio();

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                      "Info: El servicio se ha guardado correctamente",
                         ButtonType.OK);
                alert.show();
            }catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(),
                    ButtonType.OK);
                    alert.showAndWait();
            }

        }
    }
}
