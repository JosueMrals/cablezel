package com.josue.util;

import com.josue.modelo.Cliente;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalUtil {

    /**
     * crear metodo estatico que retorna una lista de clientes
     *
     * @return string[]
     */
    public static String[] obtenerClientes() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clienteService.getAll());
        String[] clientes = new String[listaClientes.size()];
        for (int i = 0; i < listaClientes.size(); i++) {
            clientes[i] = listaClientes.get(i).getPrimer_nombre() + " " + listaClientes.get(i).getSegundo_nombre()
                    + " " + listaClientes.get(i).getPrimer_apellido() + " " + listaClientes.get(i).getSegundo_apellido();
        }
        return clientes;
    }

    public static String[] obtenerCedula() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        ObservableList<Cliente> listaClientesCedula = FXCollections.observableArrayList(clienteService.getAll());
        String[] clientes = new String[listaClientesCedula.size()];
        for (int i = 0; i < listaClientesCedula.size(); i++) {
            clientes[i] = listaClientesCedula.get(i).getNum_cedula();
        }
        return clientes;
    }

}
