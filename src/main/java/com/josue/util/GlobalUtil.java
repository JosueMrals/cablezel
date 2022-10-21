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

    /**
     * crear metodo estatico que retorna una lista de clientes
     *
     * @return ObservableList<Cliente>
     */
    public static ObservableList<Cliente> obtenerClientesObservableList() {
        IGenericService<Cliente> clienteService = new GenericServiceImpl<>(Cliente.class, HibernateUtil.getSessionFactory());
        return FXCollections.observableArrayList(clienteService.getAll());
    }

    public static ObservableList<Servicio> obtenerServicios() {
        IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(servicioService.getAll());
    }

    public static ObservableList<TipoContrato> obtenerTipoContratos() {
        IGenericService<TipoContrato> tipoContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(tipoContratoService.getAll());
    }

    public static void  crearTipoContrato() {
        ObservableList<TipoContrato> listaTipoContrato = obtenerTipoContratos();
        if (listaTipoContrato.isEmpty()) {

            IGenericService<Servicio> servicioService = new GenericServiceImpl<>(Servicio.class, HibernateUtil
                    .getSessionFactory());
            Servicio servicio = servicioService.getById(1L);

            TipoContrato tipoContrato = new TipoContrato();
            tipoContrato.setCod_tipocontrato("1");
            tipoContrato.setTipo_contrato("Mensual");
            tipoContrato.setCantidad_tv("1");
            tipoContrato.setDescripcion("Contrato mensual");
            tipoContrato.setServicio(servicio);
            IGenericService<TipoContrato> tipoContratoService = new GenericServiceImpl<>(TipoContrato.class, HibernateUtil
                    .getSessionFactory());
            tipoContratoService.save(tipoContrato);
        }
    }


    public static ObservableList<Barrio> obtenerBarrios() {
        IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(barrioService.getAll());
    }

    public static void crearBarrio() {
        ObservableList<Barrio> listaBarrios = obtenerBarrios();
        if (listaBarrios.isEmpty()) {
            Barrio barrio = new Barrio();
            barrio.setNombre_barrio("Barrio 1");
            barrio.setDescripcion("Descripcion Barrio 1");
            IGenericService<Barrio> barrioService = new GenericServiceImpl<>(Barrio.class, HibernateUtil
                    .getSessionFactory());
            barrioService.save(barrio);
        }
    }

}
