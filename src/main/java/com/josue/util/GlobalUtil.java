package com.josue.util;

import com.josue.modelo.Barrio;
import com.josue.modelo.Cliente;
import com.josue.modelo.Servicio;
import com.josue.modelo.TipoContrato;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalUtil {

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

    public static String[] obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil.getSessionFactory());
        ObservableList<Barrio> listaBarrios = FXCollections.observableArrayList(barrioService.getAll());
        String[] barrios = new String[listaBarrios.size()];
        for (int i = 0; i < listaBarrios.size(); i++) {
            barrios[i] = listaBarrios.get(i).getNombre_barrio();
        }
        return barrios;
    }

    public static String[] obtenerTipoContrato() {
        IGenericService<TipoContrato> tipoContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil.getSessionFactory());
        ObservableList<TipoContrato> listaTipoContrato = FXCollections.observableArrayList(tipoContratoService.getAll());
        String[] tipoContrato = new String[listaTipoContrato.size()];
        for (int i = 0; i < listaTipoContrato.size(); i++) {
            tipoContrato[i] = listaTipoContrato.get(i).getTipo_contrato();
        }
        return tipoContrato;
    }

    public static ObservableList<Cliente> getClientes() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
    }

    public static ObservableList<Servicio> getServicios() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(servicioService.getAll());
    }

    public static ObservableList<TipoContrato> getTipoContratos() {
        IGenericService<TipoContrato> tipoContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(tipoContratoService.getAll());
    }

    public static ObservableList<Barrio> getBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(barrioService.getAll());
    }

}
