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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
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
    @FXML TableColumn<Servicio, String> colAccion;
    @FXML TextField txtBuscarServicio;

    ObservableList<Servicio> listaServicios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarServicio();
        addButtonEdit();
    }

    private void addButtonEdit() {
        colAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lapiz.png")));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(20);
                    imageView.setFitWidth(20);
                    btnEditar.setGraphic(imageView);
                    btnEditar.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                    btnEditar.setOnAction(event -> {
                        Servicio servicio = getTableView().getItems().get(getIndex());
                        txtNombre.setText(servicio.getNombre());
                        txtDescripcion.setText(servicio.getDescripcion());
                        txtPrecio.setText(String.valueOf(servicio.getPrecio()));
                        btnGuardar.setText("Actualizar");

                        btnGuardar.setOnAction(event1 -> {
                            actualizarServicio(servicio);
                            llenarServicio();
                            recargarServicio();
                        });
                    });
                    setGraphic(btnEditar);
                    setText(null);
                }
            }

            private void actualizarServicio(Servicio servicio) {
                try {
                    IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
                    servicio.setNombre(txtNombre.getText());
                    servicio.setDescripcion(txtDescripcion.getText());
                    servicio.setPrecio((float) Double.parseDouble(txtPrecio.getText()));
                    servicioService.update(servicio);

                    txtNombre.clear();
                    txtDescripcion.clear();
                    txtPrecio.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Servicio actualizado correctamente", ButtonType.OK);
                    alert.showAndWait();

                    btnGuardar.setText("Guardar");
                    btnGuardar.setOnAction(event -> {
                        guardarServicio();
                        llenarServicio();
                        tvServicios.refresh();
                    });
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al actualizar el servicio", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
    }

    public void recargarServicio() {
        txtBuscarServicio.clear();
        llenarServicio();
        tvServicios.refresh();
    }

    public void llenarServicio() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
        listaServicios = FXCollections.observableArrayList(servicioService.getAll());
        if (listaServicios.size() > 0) {
            tvServicios.setItems(listaServicios);
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Sin datos!");
        alert.setContentText("No hay datos en la tabla, por favor ingrese un servicio");
        alert.showAndWait();

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