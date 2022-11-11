package com.josue.view;

import com.josue.modelo.*;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.logging.log4j.Logger;

/**
 * Created by Josue on 09/05/2016.
 */
public class ConfiguracionesController implements Initializable {
    // logger log4j
    private static final Logger logger = LogManager.getLogger(ConfiguracionesController.class);

    // Lista de Servicios
    @FXML TextField txtNombre;
    @FXML TextField txtDescripcion;
    @FXML TextField txtPrecio;
    @FXML  Button btnGuardar;
    @FXML TableView<Servicio> tvServicios;
    @FXML TableColumn<Servicio, String> colNombre;
    @FXML TableColumn<Servicio, String> colDescripcion;
    @FXML TableColumn<Servicio, String> colPrecio;
    @FXML TableColumn<Servicio, String> colAccionServicios;
    @FXML TextField txtBuscarServicio;
    @FXML Button btnBuscarServicio;
    ObservableList<Servicio> listaServicios;


    // Tipo Contratos
    @FXML Button btBuscarTC;
    @FXML TextField txtCodigo;
    @FXML TextField txtTipoContrato;
    @FXML TextField txtCantidadTv;
    @FXML ComboBox<Servicio> cmbServicio;
    @FXML TextField txtDescripcionContrato;
    @FXML TableColumn<TipoContrato, String> colCodigo;
    @FXML TableColumn<TipoContrato, String> colTipoContrato;
    @FXML TableColumn<TipoContrato, String> colCantidadTv;
    @FXML TableColumn<TipoContrato, String> colDescripcionTipoContrato;
    @FXML TableView<TipoContrato> tvTipoContrato;
    @FXML Button btnGuardarContrato;
    @FXML Button btnGuardarBarrio;
    ObservableList<Barrio> listaBarrios;
    ObservableList<TipoContrato> listaTipoContrato;
    @FXML TextField txtBuscarTC;
    String[] tipoContratoAutoComplete = {};
    @FXML TableColumn<TipoContrato, String> colAccion;

    //Lista de Barrios
    @FXML TextField txtNombreBarrio;
    @FXML TextField txtDescripcionBarrio;
    @FXML TableView<Barrio> tvBarrios;
    @FXML TableColumn<Barrio, String> colNombreBarrio;
    @FXML TableColumn<Barrio, String > colDescripcionBarrio;
    @FXML TableColumn<Barrio, String> colAccion1;
    @FXML TextField txtBuscarBarrio;
    String[] barrioAutoComplete = {};

    // Respaldo
    public ToggleButton tbConfigurarServidor;
    public TextField txtBuscarRespaldo;
    public Button btRestaurar;
    public Button btGenerar;
    public ComboBox cbBuscarRespaldo;
    public ImageView ivBuscarRespaldo;
    public Tab tpRespaldo;
    ObservableList<ConfiguracionSistema> configuracionSistemas;
    String herramientaRespaldo = "";
    String herramienta = "";
    String host = "";
    String puerto = "";
    String usuario = "";
    String password = "";
    String baseDatos = "";
    String directorio = "";

    // Usuarios
    public TableView<Usuario> tvListaUsuarios;
    public TextField txtNombreUsuario;
    public TextField txtNickUsuario;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public ComboBox<String> cbRol;
    public Button btAgregarUsuarios;
    public TableColumn<Usuario, String> colNombreUsuario;
    public TableColumn<Usuario, String> colNickUsuario;
    public TableColumn<Usuario, String> colEmail;
    public TableColumn<Usuario, String> colPassword;
    public TableColumn<Usuario, String> colRolUsuario;
    public TableColumn<Usuario, String> colAccionUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoCompletarBarrio();
        autoCompletarTipoContrato();

        llenarTablaUsuarios();
        llenarTablaTipoContrato();
        llenarTablaBarrios();
        llenarTablaServicio();

        addButtonEdit();
        addButtonEditarServicios();

        listarServicios();

        verificarConfiguracionServidor();
    }

    private void verificarConfiguracionServidor() {
        IGenericService<ConfiguracionSistema> configuracionSistemaService = new GenericServiceImpl<>(ConfiguracionSistema.class, HibernateUtil.getSessionFactory());
        configuracionSistemas = FXCollections.observableArrayList(configuracionSistemaService.getAll());
        if (configuracionSistemas.size() > 0) {
            // cargar configuracion de manera individual
            for (ConfiguracionSistema configuracionSistema : configuracionSistemas) {
                if (configuracionSistema.getNombre().equals("servidor")) {
                    herramienta = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("host")) {
                    host = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("puerto")) {
                    puerto = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("usuario")) {
                    usuario = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("clave")) {
                    password = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("baseDatos")) {
                    baseDatos = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("respaldo")) {
                    directorio = configuracionSistema.getValor();
                }
                if (configuracionSistema.getNombre().equals("herramientaRespaldo")) {
                    herramientaRespaldo = configuracionSistema.getValor();
                }
            }

            tbConfigurarServidor.setSelected(true);
        } else {
            tbConfigurarServidor.setSelected(false);
        }
    }

    public void recargarBarrio() {
        txtBuscarBarrio.clear();
        llenarTablaBarrios();
        tvBarrios.refresh();
    }

    public void recargar() {
        txtBuscarTC.clear();
        llenarTablaTipoContrato();
        tvTipoContrato.refresh();
    }

    public void llenarTablaUsuarios() {
        try {
            ObservableList<Usuario> usuarios = GlobalUtil.getUsuarios();
            colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombrecompleto"));
            colNickUsuario.setCellValueFactory(new PropertyValueFactory<>("nickusuario"));
            colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            tvListaUsuarios.setItems(usuarios);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de usuarios", e);
        }
    }

    public void llenarTablaTipoContrato() {
        try {
            IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.getSessionFactory());
            ObservableList<TipoContrato> tpContrato = FXCollections.observableArrayList(tpContratoService.getAll());
            listaTipoContrato = tpContrato;
            colCodigo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getCod_tipocontrato()));
            colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipo_contrato"));
            colCantidadTv.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue().getCantidad_tv()));
            colDescripcionTipoContrato.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tvTipoContrato.setItems(tpContrato);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de tipo de contrato", e);
        }
    }

    private void llenarTablaBarrios() {
        try {
            IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
            ObservableList<Barrio> barrios = FXCollections.observableArrayList(barrioService.getAll());
            listaBarrios = barrios;
            colNombreBarrio.setCellValueFactory(new PropertyValueFactory<>("nombre_barrio"));
            colDescripcionBarrio.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tvBarrios.setItems(barrios);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de barrios", e);
        }
    }

    private void addButtonEdit() {
        colAccion.setCellFactory(param -> new TableCell<>() {
            final Button btnEditar = new Button();

            @Override
            public void updateItem(String item, boolean empty) {
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
                        TipoContrato tipoContrato = getTableView().getItems().get(getIndex());
                        txtCodigo.setText(tipoContrato.getCod_tipocontrato());
                        txtTipoContrato.setText(tipoContrato.getTipo_contrato());
                        txtCantidadTv.setText(String.valueOf(tipoContrato.getCantidad_tv()));
                        txtDescripcionContrato.setText(tipoContrato.getDescripcion());
                        cmbServicio.setValue(tipoContrato.getServicio());

                        btnGuardarContrato.setText("Actualizar");

                        btnGuardarContrato.setOnAction(event1 -> {
                            actualizarTipoContrato(tipoContrato);
                            llenarTablaTipoContrato();
                            recargar();
                        });
                    });
                    setGraphic(btnEditar);
                    setText(null);
                }
            }
            private void actualizarTipoContrato(TipoContrato tipoContrato) {
                try {
                    IGenericService<TipoContrato> tipoContratoService = new GenericServiceImpl<>
                            (TipoContrato.class, HibernateUtil.getSessionFactory());
                    tipoContrato.setCod_tipocontrato(txtCodigo.getText());
                    tipoContrato.setTipo_contrato(txtTipoContrato.getText());
                    tipoContrato.setCantidad_tv(String.valueOf(Integer.parseInt(txtCantidadTv.getText())));
                    tipoContrato.setDescripcion(txtDescripcionContrato.getText());
                    tipoContrato.setServicio(cmbServicio.getSelectionModel().getSelectedItem());
                    tipoContratoService.update(tipoContrato);

                    txtCodigo.clear();
                    txtTipoContrato.clear();
                    txtCantidadTv.clear();
                    txtDescripcionContrato.clear();
                    cmbServicio.getSelectionModel().clearSelection();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tipo de contrato actualizado correctamente", ButtonType.OK);
                    alert.showAndWait();

                    btnGuardarContrato.setText("Guardar");
                    btnGuardarContrato.setOnAction(event -> {
                        guardarTipoContrato();
                        llenarTablaTipoContrato();
                        tvTipoContrato.refresh();
                    });

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al actualizar el tipo de contrato aqui!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        colAccion1.setCellFactory(param -> new TableCell<>() {
            final Button btnEditar = new Button();

            @Override
            public void updateItem(String item, boolean empty) {
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
                        Barrio barrio = getTableView().getItems().get(getIndex());
                        txtNombreBarrio.setText(barrio.getNombre_barrio());
                        txtDescripcionBarrio.setText(barrio.getDescripcion());
                        btnGuardarBarrio.setText("Actualizar");

                        btnGuardarBarrio.setOnAction(event1 -> {
                            actualizarBarrio(barrio);
                            llenarTablaBarrios();
                            recargarBarrio();
                        });
                    });
                    setGraphic(btnEditar);
                    setText(null);
                }
            }

            private void actualizarBarrio(Barrio barrio) {
                try {
                    IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
                    barrio.setNombre_barrio(txtNombreBarrio.getText());
                    barrio.setDescripcion(txtDescripcionBarrio.getText());
                    barrioService.update(barrio);

                    txtNombreBarrio.clear();
                    txtDescripcionBarrio.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Barrio actualizado correctamente", ButtonType.OK);
                    alert.showAndWait();

                    btnGuardarBarrio.setText("Guardar");
                    btnGuardarBarrio.setOnAction(event -> {
                        guardarBarrio();
                        llenarTablaBarrios();
                        tvBarrios.refresh();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void autoCompletarTipoContrato() {
        tipoContratoAutoComplete = GlobalUtil.obtenerTipoContrato();
        TextFields.bindAutoCompletion(txtBuscarTC, tipoContratoAutoComplete);
        llenarTablaTipoContrato();
        tvTipoContrato.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autoCompletarBarrio() {
        barrioAutoComplete = GlobalUtil.obtenerBarrios();
        TextFields.bindAutoCompletion(txtBuscarBarrio, barrioAutoComplete);
        llenarTablaBarrios();
        tvBarrios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void buscarBarrio() {
        String barrio = txtBuscarBarrio.getText();
        if (txtBuscarBarrio.getText().isEmpty()){
            llenarTablaBarrios();
        }else {
            ObservableList<Barrio> barrios = GlobalUtil.getBarrios();
            ObservableList<Barrio> barriosFiltrados = FXCollections.observableArrayList();
            for (Barrio b : barrios){
                if (b.getNombre_barrio().toLowerCase().contains(barrio.toLowerCase())){
                    barriosFiltrados.add(b);
                }
            }
            tvBarrios.setItems(barriosFiltrados);
        }
    }

    @FXML
    private void buscarTC() {
        String tipoContrato = txtBuscarTC.getText();
        if (txtBuscarTC.getText().isEmpty()){
            llenarTablaTipoContrato();
        }else {
            ObservableList<TipoContrato> tipoContratos = GlobalUtil.getTipoContratos();
            ObservableList<TipoContrato> tipoContratosFiltrados = FXCollections.observableArrayList();
            for (TipoContrato tc : tipoContratos){
                if (tc.getTipo_contrato().toLowerCase().contains(tipoContrato.toLowerCase())){
                    tipoContratosFiltrados.add(tc);
                }
            }
            tvTipoContrato.setItems(tipoContratosFiltrados);
        }
    }

    public void guardarBarrio() {

        //Validar que los campos no esten vacios
        if (txtNombreBarrio.getText().isEmpty() || txtDescripcionBarrio.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Debe llenar todos los campos");
            alert.showAndWait();
            return;
        }
        for (Barrio barrio : listaBarrios) {
            if (barrio.getNombre_barrio().equals(txtNombreBarrio.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("El nombre del barrio ya existe");
                alert.showAndWait();
                return;
            }
        }

        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class,
                HibernateUtil.getSessionFactory());

        String nombre_barrio = txtNombreBarrio.getText();
        String descripcion = txtDescripcionBarrio.getText();

        try {
            Barrio ba = new Barrio();
            ba.setNombre_barrio(nombre_barrio);
            ba.setDescripcion(descripcion);

            barrioService.save(ba);

            txtNombreBarrio.clear();
            txtDescripcionBarrio.clear();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nuevo Barrio Ingresado Correctamente." ,
                    ButtonType.OK);
            alert.showAndWait();

            llenarTablaBarrios();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void guardarTipoContrato() {
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class,
                HibernateUtil.getSessionFactory());

        String cod_tipocontrato = txtCodigo.getText();
        String tipo_contrato = txtTipoContrato.getText();
        String cantidad_tv = txtCantidadTv.getText();
        String descripcion = txtDescripcionContrato.getText();
        Servicio servicio = cmbServicio.getValue();

        try{
            TipoContrato tc = new TipoContrato();
            tc.setCod_tipocontrato(cod_tipocontrato);
            tc.setTipo_contrato(tipo_contrato);
            tc.setCantidad_tv(cantidad_tv);
            tc.setServicio(servicio);
            tc.setDescripcion(descripcion);

            tpContratoService.save(tc);

            txtCodigo.clear();
            txtTipoContrato.clear();
            cmbServicio.getSelectionModel().clearSelection();
            txtCantidadTv.clear();
            txtDescripcionContrato.clear();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tipo de Contrato Ingresado Correctamente." ,
                    ButtonType.OK);
            alert.showAndWait();

            llenarTablaTipoContrato();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void guardarUsuarios() {
        // Validar que los campos no esten vacios
        if (txtNombreUsuario.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtNickUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || cbRol.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No se ha podido registrar el usuario");
            alert.setContentText("Debe llenar todos los campos.");
            alert.showAndWait();
        } else {
            // Validar que los datos no se repitan
            IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class,
                    HibernateUtil.getSessionFactory());
            if (usuarioService.getAll().stream().anyMatch(u ->u.getNombrecompleto().equals(txtNombreUsuario.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se ha podido registrar el usuario");
                alert.setContentText("El nombre de usuario ya existe.");
                alert.showAndWait();
            } else if (usuarioService.getAll().stream().anyMatch(u ->u.getNickusuario().equals(txtNickUsuario.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se ha podido registrar el usuario");
                alert.setContentText("El nombre de usuario ya existe.");
                alert.showAndWait();
            } else if (usuarioService.getAll().stream().anyMatch(u ->u.getEmail().equals(txtEmail.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se ha podido registrar el usuario");
                alert.setContentText("El email ya existe.");
                alert.showAndWait();
            } else {
                try {
                    // Registrar el usuario
                    Usuario usuario = new Usuario();
                    usuario.setNombrecompleto(txtNombreUsuario.getText());
                    usuario.setEmail(txtEmail.getText());
                    usuario.setNickusuario(txtNickUsuario.getText());
                    usuario.setPassword(txtPassword.getText());
                    usuario.setRol(cbRol.getValue());

                    usuarioService.save(usuario);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Usuario registrado");
                    alert.setContentText("El usuario se ha registrado con exito.");
                    alert.showAndWait();

                    // Limpiar los campos
                    txtNombreUsuario.setText("");
                    txtEmail.setText("");
                    txtNickUsuario.setText("");
                    txtPassword.setText("");
                    cbRol.getSelectionModel().clearSelection();

                    llenarTablaUsuarios();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }

            }
        }
    }

    public void listarServicios() {
        var servicios = GlobalUtil.getServicios();
        cmbServicio.setValue(null);
        cmbServicio.setItems(servicios);
    }

    public void verificarServicios() {
        if (cmbServicio.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Lista de Servicios vacía");
            alert.setContentText("Para crear un Tipo de Contrato, primero debe crear un servicio.");
            alert.showAndWait();
        }
    }

    private void addButtonEditarServicios() {
        colAccionServicios.setCellFactory(param -> new TableCell<>() {
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
                            llenarTablaServicio();
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
                        llenarTablaServicio();
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
        llenarTablaServicio();
        tvServicios.refresh();
    }

    public void llenarTablaServicio() {
        try {
            IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
            ObservableList<Servicio> servicios = FXCollections.observableArrayList(servicioService.getAll());
            listaServicios = servicios;
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tvServicios.setItems(servicios);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar la tabla de servicios", ButtonType.OK);
            alert.showAndWait();
        }
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
                llenarTablaServicio();

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

    public void tbConfigurarServidor(ActionEvent actionEvent) {
        // abrir ConfiguracionSGBD.fxml en un nuevo Stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConfiguracionesSGBD.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Configuración del Servidor");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CargarDirectorioRespaldo(Event event) {
        // cargar la ruta del directorio en el combobox
        IGenericService<ConfiguracionSistema> configuracionSistemaService = new GenericServiceImpl<>(ConfiguracionSistema.class,
                HibernateUtil.getSessionFactory());
        ObservableList<ConfiguracionSistema> confSistemas = FXCollections.observableArrayList(configuracionSistemaService.getAll());
        configuracionSistemas = confSistemas;
        if (confSistemas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No se ha configurado el servidor");
            alert.setContentText("Debe obtener una ruta de la herramienta de respaldo y un directorio de respaldo.");
            alert.showAndWait();
        } else {
            confSistemas.forEach(configuracionSistema -> {
                if(configuracionSistema.getNombre().equals("respaldo")) {
                    cbBuscarRespaldo.getItems().add(configuracionSistema.getValor());
                }
            });
        }
    }

    public void generarNuevoRespaldo(ActionEvent actionEvent) {
        // comprobar si ha seleccionado una ruta de respaldo
        if (cbBuscarRespaldo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No se ha seleccionado una ruta de respaldo");
            alert.setContentText("Debe seleccionar una ruta de respaldo.\n" +
                    "Si no tiene una ruta de respaldo, debe crear una nueva.\n" +
                    "Para crear una nueva ruta de respaldo, debe ir a la opción de Configuración del Servidor.");
            alert.showAndWait();
            return;
        }
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = null;
            ProcessBuilder processBuilder = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            String line = null;

            //herramienta = herramienta.replace("\\", "\\\\");
            //directorio = directorio.replace("\\", "\\\\");

            logger.info("Herramienta: " + herramienta);
            logger.info("Host: " + host);
            logger.info("Puerto: " + puerto);
            logger.info("Usuario: " + usuario);
            logger.info("Password: " + password);
            logger.info("Base de Datos: " + baseDatos);
            logger.info("Directorio: " + directorio);

            // crear el fichero y comprobar si existe
            File fichero = new File(directorio);

            if (fichero.exists()) {
                logger.info("El fichero existe");
                StringBuffer fechaArchivo = new StringBuffer();
                fechaArchivo.append(directorio);
                fechaArchivo.append(File.separator);
                fechaArchivo.append("respaldo" +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss")));
                fechaArchivo.append(".sql");
                logger.info("Fecha del archivo: " + fechaArchivo.toString());
                File ficheroNuevo = new File(fechaArchivo.toString());
                if (ficheroNuevo.exists()) {
                    logger.info("El fichero ya existe");
                    ficheroNuevo.delete();
                } else {
                    logger.info("El fichero no existe");
                    ficheroNuevo.createNewFile();
                }
                runtime = Runtime.getRuntime();
                processBuilder = new ProcessBuilder( herramienta, "-f", fechaArchivo.toString(),
                        "-F", "c","-v","-h", host, "-U", usuario, baseDatos);
                processBuilder.environment().put("PGPASSWORD", password);
                processBuilder.redirectErrorStream(true);
                process = processBuilder.start();
                try{
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String linel;
                    while ((linel = br.readLine()) != null) {
                        System.out.println(linel);
                    }

                    if (process.waitFor() == 0) {
                        // alertar al usuario
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Información");
                        alert.setHeaderText("Respaldo generado exitosamente");
                        alert.setContentText("El respaldo se ha generado en el directorio: " + directorio);
                        alert.showAndWait();
                    } else {
                        // alertar al usuario
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Respaldo no generado");
                        alert.setContentText("El respaldo no se ha generado en el directorio: " + directorio);
                        alert.showAndWait();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else {
                logger.info("El fichero no existe");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restaurarBaseDeDatos(ActionEvent actionEvent) {
        // verificar herramienta de restauración
        if(herramientaRespaldo.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No se ha configurado la herramienta de restauracion");
            alert.setContentText("Debe obtener una ruta de la herramienta de restauracion y un fichero.");
            alert.showAndWait();
            return;
        } else {
            if (txtBuscarRespaldo.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se ha seleccionado un respaldo");
                alert.setContentText("Debe seleccionar un respaldo.");
                alert.showAndWait();
                return;
            }
            try {
                Runtime runtime = Runtime.getRuntime();
                Process process = null;
                ProcessBuilder processBuilder = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader reader = null;
                String line = null;

                // obtener la ruta del fichero de respaldo
                String rutaRespaldo = txtBuscarRespaldo.getText();

                // crear el fichero y comprobar si existe
                File fichero = new File(rutaRespaldo);

                if (fichero.exists()) {
                    logger.info("El fichero existe");
                    runtime = Runtime.getRuntime();
                    logger.info("Herramienta: " + herramientaRespaldo);
                    processBuilder = new ProcessBuilder( herramientaRespaldo, "--verbose","--host="+host, "--username="+usuario,
                            "--dbname="+baseDatos, "--port="+puerto, "--no-password", "--clean", "--format=c",  rutaRespaldo);
                    processBuilder.environment().put("PGPASSWORD", password);
                    processBuilder.redirectErrorStream(true);
                    process = processBuilder.start();
                    try{
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String linel;
                        while ((linel = br.readLine()) != null) {
                            System.out.println(linel);
                        }

                        if (process.waitFor() == 0) {
                            // alertar al usuario
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Información");
                            alert.setHeaderText("Restauración generada exitosamente");
                            alert.setContentText("La restauración se ha generado con exito");
                            alert.showAndWait();
                        } else {
                            // alertar al usuario
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Restauracion no generada");
                            alert.setContentText("La restauración no se ha generado");
                            alert.showAndWait();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    logger.info("El fichero no existe");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void buscarRespaldoBD(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar respaldo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SQL", "*.sql")
        );
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            txtBuscarRespaldo.setText(file.getAbsolutePath());
        }
    }


}
