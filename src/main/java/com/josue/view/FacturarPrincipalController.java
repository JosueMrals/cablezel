package com.josue.view;

import com.josue.modelo.Contrato;
import com.josue.modelo.TipoContrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.josue.util.GlobalUtil.obtenerClientes;

public class FacturarPrincipalController implements Initializable {

    @FXML TableColumn<Contrato, String> colNumContrato;
    @FXML TableColumn<Contrato, String> colTipoContrato;
    @FXML
    TableColumn<Contrato, String> colCliente;
    @FXML TableColumn<Contrato, String> colPrecioContrato;
    @FXML TextField txtBuscarCliente;
    @FXML TableView<Contrato> tvBuscarClientes;

    String[] clientesAutocomplete = {};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientesAutocomplete = obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutocomplete);
        llenarClientes();
        txtBuscarCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue);
        });
    }

    public void llenarClientes() {
        // Inicializar la tabla de clientes
        IGenericService<Contrato> service = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory());
        ObservableList<Contrato> contratos = FXCollections.observableArrayList(service.getAll());
        colNumContrato.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipoContrato.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Contrato, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getTipocontrato().getTipo_contrato());
                    }
                }
        );
        colCliente.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Contrato, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getCliente().getPrimer_nombre() + " " +
                                                            param.getValue().getCliente().getSegundo_apellido() + " " +
                                                            param.getValue().getCliente().getPrimer_apellido() + " " +
                                                            param.getValue().getCliente().getSegundo_apellido());
                    }
                }
        );

        colPrecioContrato.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Contrato, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().getTipocontrato().getPrecio_contrato());
                    }
                }
        );

        tvBuscarClientes.setItems(contratos);
    }

    public void mostrarSecundaria(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FacturaDiseño.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Visualización de la factura");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
