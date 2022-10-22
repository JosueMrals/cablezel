package com.josue.view;

import com.josue.modelo.Barrio;
import com.josue.modelo.Cliente;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import com.josue.util.GlobalUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

public class ClientesController implements Initializable {
    private static final Logger logger = LogManager.getLogger(ClientesController.class);
    @FXML Button btnGuardar;
    @FXML TextField txtBuscarCedula;
    @FXML TextField txtBuscarNombre;
    @FXML Button btnRecargar;
    @FXML Button btnBuscarNombre;
    @FXML Button btnBuscarCedula;
    @FXML TextField txtNumCedula;
    @FXML TextField txtPrimerNombre;
    @FXML TextField txtSegundoNombre;
    @FXML TextField txtPrimerApellido;
    @FXML TextField txtSegundoApellido;
    @FXML TextArea txtDireccion;
    @FXML ComboBox<Barrio> cbBarrio;
    @FXML TextField txtNumTelefono;
    @FXML TableColumn<Cliente, String> colCedula;
    @FXML TableColumn<Cliente, String> colNombre;
    @FXML TableColumn<Cliente, String> colDireccion;
    @FXML TableColumn<Cliente, String> colBarrio;
    @FXML TableColumn<Cliente, String> colTelefono;
    @FXML TableColumn<Cliente, String> colAccion;
    @FXML TableView<Cliente> tvClientes;
    Boolean hayClientes = false;
    String[] clientesAutocomplete = {};
    String[] cedulaAutocomplete = {};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hayClientes = getClientes().size() > 0;
        listarBarrios();
        if (hayClientes) {
            llenarClientes();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("No hay clientes registrados");
            alert.setContentText("Por favor registre un nuevo cliente");
            alert.showAndWait();
            logger.info("No hay clientes registrados");
        }
        autoCompletarNombre();
        autoCompletarCedula();
        addButtonEditar();
    }

    public ObservableList<Cliente> getClientes() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
    }

    public void autoCompletarNombre() {
            clientesAutocomplete = GlobalUtil.obtenerClientes();
            TextFields.bindAutoCompletion(txtBuscarNombre, clientesAutocomplete);
            llenarClientes();
            txtBuscarNombre.textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("oldValue " + oldValue);
                System.out.println("newValue " + newValue);
            });
            tvClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void autoCompletarCedula() {
        cedulaAutocomplete = GlobalUtil.obtenerCedula();
        TextFields.bindAutoCompletion(txtBuscarCedula, cedulaAutocomplete);
        llenarClientes();
        txtBuscarNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue " + oldValue);
            System.out.println("newValue " + newValue);
        });
        tvClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void listarBarrios(){
        var barrios = obtenerBarrios();
        cbBarrio.setValue(null);
        cbBarrio.setItems(barrios);
        cbBarrio.setPromptText("Seleccione un barrio");
    }

    public void llenarClientes() {
        ObservableList<Cliente> clientes = getClientes();
        colCedula.setCellValueFactory(new PropertyValueFactory<>("num_cedula"));
        colNombre.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrimer_nombre()
                        + " " + param.getValue().getSegundo_nombre() + " " +
                        param.getValue().getPrimer_apellido() + " " +
                        param.getValue().getSegundo_apellido())
        );
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colBarrio.setCellValueFactory(new PropertyValueFactory<>("barrio"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("num_telefono"));
        tvClientes.setItems(clientes);
    }

    public void registrarClientes() {

        //Validar que los campos no esten vacios
        if (txtNumCedula.getText().isEmpty() || txtPrimerNombre.getText().isEmpty() || txtPrimerApellido.
                getText().isEmpty() || txtDireccion.getText().isEmpty() || txtNumTelefono.getText().isEmpty()
                || cbBarrio.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar cliente");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();

        } else {
            // Validar que no se repita el numero de cedula
            IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.
                    getSessionFactory());
            ObservableList<Cliente> clientes = getClientes();
            for (Cliente cliente : clientes) {
                if (cliente.getNum_cedula().equals(txtNumCedula.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al registrar cliente");
                    alert.setContentText("El numero de cedula ya existe");
                    alert.showAndWait();
                    return;
                }
            }

            // Obtener los datos del formulario
            String numcedula = txtNumCedula.getText();
            String primernombre = txtPrimerNombre.getText();
            String segundonombre = txtSegundoNombre.getText();
            String primerapellido = txtPrimerApellido.getText();
            String segundo_apellido = txtSegundoApellido.getText();
            String direccion = txtDireccion.getText();
            Barrio barrio = cbBarrio.getValue();
            String numtelefono = txtNumTelefono.getText();

            // Crear el cliente
            try {
                Cliente cl = new Cliente();

                cl.setNum_cedula(numcedula);
                cl.setPrimer_nombre(primernombre);
                cl.setSegundo_nombre(segundonombre);
                cl.setPrimer_apellido(primerapellido);
                cl.setSegundo_apellido(segundo_apellido);
                cl.setDireccion(direccion);
                cl.setBarrio(barrio);
                cl.setNum_telefono(numtelefono);

                // Guardar el cliente
                clienteService.save(cl);

                // Limpiar el formulario
                txtNumCedula.clear();
                txtPrimerNombre.clear();
                txtSegundoNombre.clear();
                txtSegundoApellido.clear();
                txtPrimerApellido.clear();
                txtSegundoApellido.clear();
                txtDireccion.clear();
                cbBarrio.getSelectionModel().clearSelection();
                txtNumTelefono.clear();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El cliente se insertó correctamente.",
                        ButtonType.OK);
                alert.showAndWait();

                llenarClientes();


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void buscarCedula() {
        String cedulaCliente = txtBuscarCedula.getText();
        if (txtBuscarCedula.getText() == null) {
            llenarClientes();
        } else {
            ObservableList<Cliente> clientes = getClientes();
            ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
            for (Cliente cliente : clientes) {
                if (cliente.getNum_cedula().contains(cedulaCliente)) {
                    clientesFiltrados.add(cliente);
                }
            }
            tvClientes.setItems(clientesFiltrados);
        }
    }

    public void buscarNombre() {
        String nombreCliente = txtBuscarNombre.getText();
        if (txtBuscarNombre.getText() == null) {
            llenarClientes();
        } else {
            ObservableList<Cliente> clientes = getClientes();
            ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
            for (Cliente cliente : clientes) {
                if ((cliente.getPrimer_nombre() + " " + cliente.getSegundo_nombre()
                        + " " + cliente.getPrimer_apellido() + " " + cliente.getSegundo_apellido())
                        .contains(nombreCliente)) {
                    clientesFiltrados.add(cliente);
                }
            }
            tvClientes.setItems(clientesFiltrados);
        }
    }

    public ObservableList<Barrio> obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.
                getSessionFactory());
        return FXCollections.observableArrayList(barrioService.getAll());
    }

    public void recargar() {
        txtBuscarNombre.clear();
        txtBuscarCedula.clear();
        llenarClientes();
        tvClientes.refresh();
    }

    public void verificarBarrio() {
        if (cbBarrio.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar cliente");
            alert.setContentText("Por favor, ingrese un barrio en la base de datos.");
            alert.showAndWait();
        }
    }

    public void addButtonEditar() {
        colAccion.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
                final TableCell<Cliente, String> cell = new TableCell<>() {
                    final Button btnEditar = new Button();
                    final Button btnEliminar = new Button();
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            // Imagen del boton
                            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lapiz.png")));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            btnEditar.setGraphic(imageView);
                            btnEditar.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                            btnEditar.setOnAction(event -> {
                                Cliente cliente = getTableView().getItems().get(getIndex());
                                txtNumCedula.setText(cliente.getNum_cedula());
                                txtPrimerNombre.setText(cliente.getPrimer_nombre());
                                txtSegundoNombre.setText(cliente.getSegundo_nombre());
                                txtPrimerApellido.setText(cliente.getPrimer_apellido());
                                txtSegundoApellido.setText(cliente.getSegundo_apellido());
                                txtDireccion.setText(cliente.getDireccion());
                                cbBarrio.setValue(cliente.getBarrio());
                                txtNumTelefono.setText(cliente.getNum_telefono());

                                btnGuardar.setText("Actualizar");
                                btnGuardar.setOnAction(event1 -> {
                                    actualizarCliente(cliente);
                                    llenarClientes();
                                    recargar();
                                });
                            });

                            // Imagen del boton
                            Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/borrar.png")));
                            ImageView imageView2 = new ImageView(image2);
                            imageView2.setFitHeight(20);
                            imageView2.setFitWidth(20);
                            btnEliminar.setGraphic(imageView2);
                            btnEliminar.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                            HBox pane = new HBox(btnEditar, btnEliminar);
                            setGraphic(pane);
                            llenarClientes();
                        }
                    }

                    private void actualizarCliente(Cliente cliente) {
                        try {
                            IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.
                                    getSessionFactory());
                            cliente.setNum_cedula(txtNumCedula.getText());
                            cliente.setPrimer_nombre(txtPrimerNombre.getText());
                            cliente.setSegundo_nombre(txtSegundoNombre.getText());
                            cliente.setPrimer_apellido(txtPrimerApellido.getText());
                            cliente.setSegundo_apellido(txtSegundoApellido.getText());
                            cliente.setDireccion(txtDireccion.getText());
                            cliente.setBarrio(cbBarrio.getValue());
                            cliente.setNum_telefono(txtNumTelefono.getText());
                            clienteService.update(cliente);

                            txtNumCedula.clear();
                            txtPrimerNombre.clear();
                            txtSegundoNombre.clear();
                            txtPrimerApellido.clear();
                            txtSegundoApellido.clear();
                            txtDireccion.clear();
                            cbBarrio.getSelectionModel().clearSelection();
                            txtNumTelefono.clear();

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info: El cliente se actualizó correctamente.",
                                    ButtonType.OK);
                            alert.showAndWait();

                            btnGuardar.setText("Guardar");
                            btnGuardar.setOnAction(event -> {
                                registrarClientes();
                                llenarClientes();
                                tvClientes.refresh();
                            });

                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                };
                return cell;
            }
        });
    }

}
