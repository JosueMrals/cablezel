package com.josue.view;

import com.josue.modelo.Contrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.GlobalUtil;
import com.josue.util.HibernateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacturarPrincipalController implements Initializable {

    public Button btnBuscar;
    @FXML TableColumn<Contrato, String> colAccion;
    @FXML Button btFacturar;
    @FXML TableColumn<Contrato, String> colNumContrato;
    @FXML TableColumn<Contrato, String> colTipoContrato;
    @FXML TableColumn<Contrato, String> colCliente;
    @FXML TableColumn<Contrato, String> colPrecioContrato;
    @FXML TextField txtBuscarCliente;
    @FXML TableView<Contrato> tvBuscarClientes;

    String[] clientesAutocomplete = {};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientesAutocomplete = GlobalUtil.obtenerClientes();
        TextFields.bindAutoCompletion(txtBuscarCliente, clientesAutocomplete);
        llenarClientes();
        txtBuscarCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue: " + oldValue);
            System.out.println("newValue: " + newValue);
        });
        tvBuscarClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addFacturarButtonToTable();

    }

    public void llenarClientes() {
        IGenericService<Contrato> service = new GenericServiceImpl<>(Contrato.class, HibernateUtil.getSessionFactory());
        ObservableList<Contrato> contratos = FXCollections.observableArrayList(service.getAll());
        colNumContrato.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipoContrato.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getTipocontrato().getTipo_contrato())
        );
        colCliente.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCliente().getPrimer_nombre() + " " +
                                                    param.getValue().getCliente().getSegundo_nombre() + " " +
                                                    param.getValue().getCliente().getPrimer_apellido() + " " +
                                                    param.getValue().getCliente().getSegundo_apellido())
        );

        tvBuscarClientes.setItems(contratos);
    }

    private void addFacturarButtonToTable() {
        colAccion.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contrato, String> call(TableColumn<Contrato, String> param) {
                final TableCell<Contrato, String> cell = new TableCell<>() {
                    final Button btn = new Button("Facturar");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                btn.setStyle("-fx-background-color:  #339933");
                                btn.setStyle("-fx-background-color:  #339933");
                                Contrato contrato = getTableView().getItems().get(getIndex());
                                System.out.println("Contrato: " + contrato);
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Facturar.fxml"));
                                    AnchorPane root = loader.load();
                                    FacturarController controller = loader.getController();
                                    controller.setContrato(contrato);
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (Exception e) {
                                    Logger.getLogger(FacturarPrincipalController.class.getName()).log(Level.SEVERE, null, e);
                                }
                            });

                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }
    
    public void buscarCliente() {
        String nombreCliente = txtBuscarCliente.getText();
        IGenericService<Contrato> service = new GenericServiceImpl<>(Contrato.class, HibernateUtil.
                getSessionFactory());
        ObservableList<Contrato> contratos = FXCollections.observableArrayList(service.getAll());
        ObservableList<Contrato> contratosFiltrados = FXCollections.observableArrayList();
        for (Contrato contrato : contratos) {
            if ((contrato.getCliente().getPrimer_nombre()
                    + " " + contrato.getCliente().getSegundo_nombre()
                    + " " + contrato.getCliente().getPrimer_apellido()
                    + " " + contrato.getCliente().getSegundo_apellido()).contains(nombreCliente))  {
                contratosFiltrados.add(contrato);
            }
        }
        tvBuscarClientes.setItems(contratosFiltrados);
    }


    public void mostrarSecundaria(ActionEvent actionEvent) {
        if (obtenerDatos()) {
            try {
                ((Node)actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FacturaSecundaria.fxml"));
                AnchorPane root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Facturar");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public boolean obtenerDatos(){
        if (tvBuscarClientes.getSelectionModel().getSelectedItems() != null) {
            ObservableList<Contrato> contratos = FXCollections.observableArrayList(tvBuscarClientes.getSelectionModel()
                    .getSelectedItems());
            for (Contrato contrato : contratos) {
                System.out.println("Contrato seleccionado: " + contrato.getCliente().getPrimer_nombre());
            }
        }
        return false;
    }

}
