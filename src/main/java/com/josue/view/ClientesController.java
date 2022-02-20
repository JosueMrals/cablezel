package com.josue.view;

import com.josue.modelo.Usuario;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {
    @FXML
    TableView tvClientes;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
