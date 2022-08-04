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
    @FXML TextField txtPreciocontrato;
    @FXML TextField txtDescripcionContrato;
    @FXML TableColumn<TipoContrato, String> colCodigo;
    @FXML TableColumn<TipoContrato, String> colTipoContrato;
    @FXML TableColumn<TipoContrato, String> colCantidadTv;
    @FXML TableColumn<TipoContrato, String> colDescripcionTipoContrato;
    @FXML TableView<TipoContrato> tvTipoContrato;
    @FXML Button btnLimpiarContrato;
    @FXML Button btnGuardarContrato;
    @FXML Button btnEditarContrato;

    ObservableList<Barrio> listaBarrios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        llenarBarrio();  // llenar la tabla de barrios
        llenarTipoContrato(); // llenar la tabla de tipo de contrato

        colocarImagenBotones(); // colocar imagenes a los botones
        textoDescripcionBotones(); // colocar texto a los botones

    }
    public void llenarTipoContrato() {

        // llenar la tabla de tipo de contrato
        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.
                getSessionFactory());
        ObservableList<TipoContrato> tpContrato = FXCollections.observableArrayList(tpContratoService.getAll());
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("cod_tipocontrato"));
        colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipo_contrato"));
        colCantidadTv.setCellValueFactory(new PropertyValueFactory<>("cantidad_tv"));
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

    public ObservableList<Barrio> getListaBarrios() {
         return listaBarrios;
    }

    public void setListaBarrios(ObservableList<Barrio> listaBarrios) {
        this.listaBarrios = listaBarrios;
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

        //Validar que no se repita el nombre del barrio
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

        //Validar que los campos no esten vacios
        if(txtCodigo.getText().isEmpty() || txtTipoContrato.getText().isEmpty() || txtCantidadTv.getText().isEmpty() ||
                txtPreciocontrato.getText().isEmpty() || txtDescripcionContrato.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No puede haber campos vacios");
            alert.showAndWait();
            return;
        }

        //Validar que no se repita el tipo de contrato
        Iterable<? extends TipoContrato> listaTipoContrato = null;
        for (TipoContrato tipoContrato : listaTipoContrato) {
            if (tipoContrato.getTipo_contrato().equals(txtTipoContrato.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("El tipo de contrato ya existe");
                alert.showAndWait();
                return;
            }
        }

        IGenericService<TipoContrato> tpContratoService = new GenericServiceImpl<>(TipoContrato.class,
                HibernateUtil.getSessionFactory());

        String cod_tipocontrato = txtCodigo.getText();
        String tipo_contrato = txtTipoContrato.getText();
        String cantidad_tv = txtCantidadTv.getText();
        String precio = txtPreciocontrato.getText();
        String descripcion = txtDescripcionContrato.getText();

        try{
            TipoContrato tc = new TipoContrato();
            tc.setCod_tipocontrato(cod_tipocontrato);
            tc.setTipo_contrato(tipo_contrato);
            tc.setCantidad_tv(cantidad_tv);
            tc.setPrecio_contrato(precio);
            tc.setDescripcion(descripcion);

            tpContratoService.save(tc);

            txtCodigo.clear();
            txtTipoContrato.clear();
            txtCantidadTv.clear();
            txtPreciocontrato.clear();
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

    private void colocarImagenBotones() {
        URL linkLimpiar = getClass().getResource("/images/dust.png");
        URL linkGuardar = getClass().getResource("/images/floppy-disk.png");
        URL linkEditar = getClass().getResource("/images/editar.png");

        Image imagenLimpiar = new Image(linkLimpiar.toString(),30,30,false,true);
        Image imagenGuardar = new Image(linkGuardar.toString(),30,30,false,true);
        Image imagenEditar = new Image(linkEditar.toString(),30,30,false,true);

        btnLimpiarContrato.setGraphic(new ImageView(imagenLimpiar));
        btnGuardarContrato.setGraphic(new ImageView(imagenGuardar));
        btnEditarContrato.setGraphic(new ImageView(imagenEditar));
    }

    private void textoDescripcionBotones() {
        btnLimpiarContrato.setTooltip(new Tooltip("Limpiar"));
        btnGuardarContrato.setTooltip(new Tooltip("Guardar"));
        btnEditarContrato.setTooltip(new Tooltip("Editar"));
    }
}
