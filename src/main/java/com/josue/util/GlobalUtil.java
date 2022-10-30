package com.josue.util;

import com.josue.modelo.*;
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

    public static String[] obtenerServicios() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil.getSessionFactory());
        ObservableList<Servicio> listaServicios = FXCollections.observableArrayList(servicioService.getAll());
        String[] servicios = new String[listaServicios.size()];
        for (int i = 0; i < listaServicios.size(); i++) {
            servicios[i] = listaServicios.get(i).getNombre();
        }
        return servicios;
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

    public static String[] obtenerContratos() {
        IGenericService< Contrato> contratoService = new GenericServiceImpl<>( Contrato.class, HibernateUtil.getSessionFactory());
        ObservableList< Contrato> listaContratos = FXCollections.observableArrayList(contratoService.getAll());
        String[] contratos = new String[listaContratos.size()];
        for (int i = 0; i < listaContratos.size(); i++) {
            contratos[i] = listaContratos.get(i).getCliente().getPrimer_nombre() + " " + listaContratos.get(i).getCliente().getSegundo_nombre()
                    + " " + listaContratos.get(i).getCliente().getPrimer_apellido() + " " + listaContratos.get(i).getCliente().getSegundo_apellido();
        }
        return contratos;
    }

    public static String[] obtenerUsuarios() {
        IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil.getSessionFactory());
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarioService.getAll());
        String[] usuarios = new String[listaUsuarios.size()];
        for (int i = 0; i < listaUsuarios.size(); i++) {
            usuarios[i] = listaUsuarios.get(i).getNickusuario();
        }
        return usuarios;
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

    public static ObservableList<Contrato> getContratos() {
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(contratoService.getAll());
    }

    public static ObservableList<Rol> getRoles() {
        IGenericService<Rol> rolService = new GenericServiceImpl<>(Rol.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(rolService.getAll());
    }

    public static ObservableList<Usuario> getUsuarios() {
        IGenericService<Usuario> usuarioService = new GenericServiceImpl<>(Usuario.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(usuarioService.getAll());
    }

    public static ObservableList<DetallePago> getDetallePago() {
        IGenericService<DetallePago> detallePagoService = new GenericServiceImpl<>(DetallePago.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(detallePagoService.getAll());
    }

}
