package com.josue.view;

import com.josue.modelo.Barrio;
import com.josue.modelo.TipoContrato;
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
import java.util.ResourceBundle;

/**
 * Created by Josue on 09/05/2016.
 */
public class BarrioController implements Initializable {

    @FXML TextField txtNombreBarrio;
    @FXML TextField txtDescripcionBarrio;
    @FXML TableView<Barrio> tvBarrios;
    @FXML TableColumn<Barrio, String> colNombreBarrio;
    @FXML TableColumn<Barrio, String > colDescripcionBarrio;
    @FXML TextField txtCodigo;
    @FXML TextField txtTipoContrato;
    @FXML TextField txtCantidadTv;
    @FXML TextField txtDescripcionContrato;
    @FXML TableColumn<TipoContrato, String> colCodigo;
    @FXML TableColumn<TipoContrato, String> colTipoContrato;
    @FXML TableColumn<TipoContrato, String> colCantidadTv;
    @FXML TableColumn<TipoContrato, String> colDescripcionTipoContrato;
    @FXML TableView<TipoContrato> tvTipoContrato;
    @FXML Button btnLimpiarContrato;
    @FXML Button btnGuardarContrato;
    @FXML Button btnEditarContrato;
    @FXML Button btnGuardarBarrio;
    ObservableList<Barrio> listaBarrios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        llenarBarrio();
        llenarTipoContrato();

        colocarImagenBotones();
    }
    /**
     * Metodo llenar la tabla de tipo de contrato
     * @author Josue
     */
    public void llenarTipoContrato() {
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.
                getSessionFactory());
        ObservableList<TipoContrato> tpContrato = FXCollections.observableArrayList(tpContratoService.getAll());
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("cod_tipocontrato"));
        colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipo_contrato"));
        colCantidadTv.setCellValueFactory(new PropertyValueFactory<>("cantidad_tv"));
        colDescripcionTipoContrato.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tvTipoContrato.setItems(tpContrato);
    }
    /**
     * Metodo para llenar la tabla de barrios
     * @author Josue
     */
    public void llenarBarrio() { // llenar la tabla de barrios
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
        ObservableList<Barrio> barrios = FXCollections.observableArrayList(barrioService.getAll());
        listaBarrios = barrios;
        colNombreBarrio.setCellValueFactory(new PropertyValueFactory<>("nombre_barrio"));
        colDescripcionBarrio.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tvBarrios.setItems(barrios);
    }
    /**
     * Metodo para guardar un barrio
     * @author Josue
     */
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
    /**
     * Metodo para guardar un tipo de contrato
     * @author Josue
     */
    public void guardarTipoContrato() {
        if(txtCodigo.getText().isEmpty() || txtTipoContrato.getText().isEmpty() || txtCantidadTv.getText().isEmpty() ||
                txtDescripcionContrato.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No puede haber campos vacios");
            alert.showAndWait();
            return;
        }
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class,
                HibernateUtil.getSessionFactory());

        String cod_tipocontrato = txtCodigo.getText();
        String tipo_contrato = txtTipoContrato.getText();
        String cantidad_tv = txtCantidadTv.getText();
        String descripcion = txtDescripcionContrato.getText();

        try{
            TipoContrato tc = new TipoContrato();
            tc.setCod_tipocontrato(cod_tipocontrato);
            tc.setTipo_contrato(tipo_contrato);
            tc.setCantidad_tv(cantidad_tv);
            tc.setDescripcion(descripcion);

            tpContratoService.save(tc);

            txtCodigo.clear();
            txtTipoContrato.clear();
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
    /**
     * Metodo para colocar imagen a los botones
     * @author Josue
     */
    private void colocarImagenBotones() {
        URL linkLimpiar = getClass().getResource("/images/dust.png");
        URL linkGuardar = getClass().getResource("/images/floppy-disk.png");
        URL linkEditar = getClass().getResource("/images/editar.png");
        URL linkGuardarBarrio = getClass().getResource("/images/floppy-disk.png");

        assert linkLimpiar != null;
        assert linkGuardar != null;
        assert linkEditar != null;

        Image imagenLimpiar = new Image(linkLimpiar.toString(),30,30,false,true);
        Image imagenGuardar = new Image(linkGuardar.toString(),30,30,false,true);
        Image imagenEditar = new Image(linkEditar.toString(),30,30,false,true);

        assert linkGuardarBarrio != null;
        Image imagenGuardarBarrio = new Image(linkGuardarBarrio.toString(),30,30,false,true);

        btnLimpiarContrato.setGraphic(new ImageView(imagenLimpiar));
        btnGuardarContrato.setGraphic(new ImageView(imagenGuardar));
        btnEditarContrato.setGraphic(new ImageView(imagenEditar));
        btnGuardarBarrio.setGraphic(new ImageView(imagenGuardarBarrio));

        btnLimpiarContrato.setTooltip(new Tooltip("Limpiar"));
        btnGuardarContrato.setTooltip(new Tooltip("Guardar"));
        btnEditarContrato.setTooltip(new Tooltip("Editar"));
        btnGuardarBarrio.setTooltip(new Tooltip("Guardar"));
    }

}
