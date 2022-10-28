package com.josue.view;

import com.josue.modelo.Barrio;
import com.josue.modelo.Servicio;
import com.josue.modelo.TipoContrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by Josue on 09/05/2016.
 */
public class BarrioController implements Initializable {

    // logger log4j
    private static final Logger logger = Logger.getLogger(String.valueOf(BarrioController.class));
    @FXML TextField txtCantidadTv1;
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
    @FXML TableColumn<TipoContrato, String> colAccion;
    @FXML TableColumn<Barrio, String> colAccion1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarBarrio();
        llenarTipoContrato();
        listarServicios();
        autoCompletarBarrio();
        autoCompletarTipoContrato();
        addButtonEdit();
        addButtonEditBarrios();
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
}
