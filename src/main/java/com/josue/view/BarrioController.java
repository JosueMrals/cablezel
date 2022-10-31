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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.logging.log4j.Logger;

/**
 * Created by Josue on 09/05/2016.
 */
public class BarrioController implements Initializable {

    // logger log4j
    private static final Logger logger = LogManager.getLogger(BarrioController.class);
    public Button btBuscarTC;
    public ToggleButton tbConfigurarServidor;
    public TextField txtUsuario;
    public ComboBox<Rol> cbRol;
    public TableView<Rol> tvRoles;
    public TableColumn<Rol, String> colNick;
    public TableColumn<Rol, String> colRol;
    public TableColumn<Rol, String> colDescripcion;
    public TableColumn<Rol, String> colAccion2;
    public TextField txtBuscarUsuario;
    public Button btnBuscarUsuario;
    public TextArea txtDescripcion;
    public Button btnGuardarRol;
    public TextField txtBuscarRespaldo;
    public Button btRestaurar;
    public Button btBuscarRespaldo;
    public Button btGenerar;
    public ComboBox cbBuscarRespaldo;
    public ImageView ivBuscarRespaldo;
    @FXML TextField txtNombreBarrio;
    @FXML TextField txtDescripcionBarrio;
    @FXML TableView<Barrio> tvBarrios;
    @FXML TableColumn<Barrio, String> colNombreBarrio;
    @FXML TableColumn<Barrio, String > colDescripcionBarrio;
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
    @FXML TextField txtBuscarBarrio;
    @FXML TextField txtBuscarTC;
    String[] barrioAutoComplete = {};
    String[] tipoContratoAutoComplete = {};
    String[] rolAutoComplete = {};
    @FXML TableColumn<TipoContrato, String> colAccion;
    @FXML TableColumn<Barrio, String> colAccion1;

    Usuario usuarioSeleccionado;
    ObservableList<Usuario> listaUsuarios;
    ObservableList<ConfiguracionSistema> configuracionSistemas;
    String herramientaRespaldo = "";
    String herramienta = "";
    String host = "";
    String puerto = "";
    String usuario = "";
    String password = "";
    String baseDatos = "";
    String directorio = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarBarrio();
        llenarTipoContrato();
        listarServicios();
        llenarRoles();
        autoCompletarBarrio();
        autoCompletarTipoContrato();
        addButtonEdit();
        addButtonEditBarrios();
        autoCompletarRol();
        verificarServicios();
        listarUsuarios();
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

    private void addButtonEditBarrios() {
        colAccion1.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Barrio, String> call(TableColumn<Barrio, String> param) {
                final TableCell<Barrio, String> cell = new TableCell<>() {
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
                                    llenarBarrio();
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
                                llenarBarrio();
                                tvBarrios.refresh();
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                return cell;
            }
        });
    }


    public void recargarBarrio() {
        txtBuscarBarrio.clear();
        llenarBarrio();
        tvBarrios.refresh();
    }
    public void recargar() {
        txtBuscarTC.clear();
        llenarTipoContrato();
        tvTipoContrato.refresh();
    }

    public void recargarRol() {
        txtBuscarUsuario.clear();
        llenarRoles();
        tvRoles.refresh();
    }

    private void llenarRoles() {
        try {
            IGenericService<Rol> rolService = new GenericServiceImpl<>(Rol.class, HibernateUtil.getSessionFactory());
            ObservableList<Rol> listaRoles = FXCollections.observableArrayList(rolService.getAll());
            colNick.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getUsuario().getNickusuario()));
            colRol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombre()));
            colDescripcion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDescripcion()));
            tvRoles.setItems(listaRoles);
        } catch (Exception e) {
            logger.error("Error al llenar la tabla de roles", e);
        }

    }

    private void addButtonEdit() {
        colAccion.setCellFactory(new Callback<>() {
            @Override
            public TableCell<TipoContrato, String> call(TableColumn<TipoContrato, String> param) {
                final TableCell<TipoContrato, String> cell = new TableCell<>() {
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
                                    llenarTipoContrato();
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
                                llenarTipoContrato();
                                tvTipoContrato.refresh();
                            });

                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al actualizar el tipo de contrato aqui!", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void autoCompletarTipoContrato() {
        tipoContratoAutoComplete = GlobalUtil.obtenerTipoContrato();
        TextFields.bindAutoCompletion(txtBuscarTC, tipoContratoAutoComplete);
        llenarBarrio();
        tvTipoContrato.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autoCompletarBarrio() {
        barrioAutoComplete = GlobalUtil.obtenerBarrios();
        TextFields.bindAutoCompletion(txtBuscarBarrio, barrioAutoComplete);
        llenarBarrio();
        tvBarrios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void autoCompletarRol() {
        rolAutoComplete = GlobalUtil.obtenerUsuarios();
        TextFields.bindAutoCompletion(txtBuscarUsuario, rolAutoComplete);
        llenarRoles();
        tvRoles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void buscarRol() {
        String rol = txtBuscarUsuario.getText();
        if (rol.isEmpty()) {
            llenarRoles();
        } else {
            try {
                ObservableList<Rol> roles = GlobalUtil.getRoles();
                ObservableList<Rol> rolesFiltrados = FXCollections.observableArrayList();
                for (Rol r : roles) {
                    if (r.getNombre().toLowerCase().contains(rol.toLowerCase())) {
                        rolesFiltrados.add(r);
                    }
                }
                tvRoles.setItems(rolesFiltrados);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void buscarBarrio() {
        String barrio = txtBuscarBarrio.getText();
        if (txtBuscarBarrio.getText().isEmpty()){
            llenarBarrio();
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
            llenarTipoContrato();
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

    public void llenarTipoContrato() {
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.getSessionFactory());
        ObservableList<TipoContrato> tpContrato = FXCollections.observableArrayList(tpContratoService.getAll());
        listaTipoContrato = tpContrato;
        colCodigo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getCod_tipocontrato()));
        colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipo_contrato"));
        colCantidadTv.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue().getCantidad_tv()));
        colDescripcionTipoContrato.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tvTipoContrato.setItems(tpContrato);
    }

    public void llenarBarrio() { // llenar la tabla de barrios
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
        ObservableList<Barrio> barrios = FXCollections.observableArrayList(barrioService.getAll());
        listaBarrios = barrios;
        colNombreBarrio.setCellValueFactory(new PropertyValueFactory<>("nombre_barrio"));
        colDescripcionBarrio.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tvBarrios.setItems(barrios);
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

            llenarBarrio();
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

            llenarTipoContrato();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void listarUsuarios() {
        rolAutoComplete = GlobalUtil.obtenerUsuarios();
        listaUsuarios = GlobalUtil.getUsuarios();
        TextFields.bindAutoCompletion(txtUsuario, rolAutoComplete);
    }

    @FXML
    public void guardarRol() {
        IGenericService<Rol> rolService = new GenericServiceImpl<>(Rol.class,
                HibernateUtil.getSessionFactory());

        String usuario = txtUsuario.getText();
        String roll = String.valueOf(cbRol.getValue());
        String descripcion = txtDescripcion.getText();

        Usuario usuario1 = null;
        for (Usuario usuario2 : listaUsuarios) {
            if (usuario2.getNickusuario().equals(usuario)) {
                usuario1 = usuario2;
                usuarioSeleccionado = usuario1;
            }
        }

        try{
            Rol rol = new Rol();
            rol.setUsuario(usuario1);
            rol.setNombre(roll);
            rol.setDescripcion(descripcion);
            rolService.save(rol);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Rol Ingresado Correctamente." ,
                    ButtonType.OK);
            alert.showAndWait();

            llenarRoles();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    public ObservableList<Servicio> obtenerServicios() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(servicioService.getAll());
    }

    public void listarServicios() {
        var servicios = obtenerServicios();
        cmbServicio.setValue(null);
        cmbServicio.setItems(servicios);
    }

    public void verificarServicios() {
        if (cmbServicio.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Lista de Servicios vacía");
            alert.setContentText("Para crear un Tipo de Contrato nuevo primero debe crear un servicio.");
            alert.showAndWait();
        }
    }

    public void obtenerUsuarioSeleccionado() {
        String usuario = txtUsuario.getText();
        for (Usuario u : listaUsuarios) {
            if (u.getNickusuario().equals(usuario)) {
                usuarioSeleccionado = u;
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
