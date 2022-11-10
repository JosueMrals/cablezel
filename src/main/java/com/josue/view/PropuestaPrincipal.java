package com.josue.view;

import com.josue.modelo.*;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import com.josue.util.ManejadorUsuario;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropuestaPrincipal extends Application implements Initializable {
    static final Logger logger = LogManager.getLogger(PropuestaPrincipal.class);
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger
            (ContratoController.class.getName());

    public BorderPane panelPadre;
    public BorderPane panePrincipal;
    public Button btnServicios;
    public Button btPrincipalConfig;
    public Button btPrincipalServicios;
    public Button btPrincipalContratos;
    public Button btPrincipalFacturar;
    public Button btPrincipalClientes;
    public AnchorPane panelCentral;
    @FXML Button btnConfig;
    @FXML Button btnInicio;
    @FXML Button btnCli;
    @FXML Button btnContratos;
    @FXML Button btnFacturar;
    @FXML Button btnSalir;
    @FXML Button btMostrar;
    @FXML Button btOcultar;
    @FXML AnchorPane paneSlide;
    @FXML AnchorPane centralBottom;
    @FXML AnchorPane centralTop;

    Usuario usuario;
    Servicio servicioSeleccionado;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSalir.setOnMouseClicked(event -> System.exit(0));
        ocultarPanel();
        mostrarPanel();

        paneSlide.setTranslateX(-0);
        panelPadre.setTranslateX(0);

        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        usuario = manejador.getUsuario();

        generarFacturas();

    }

    private void generarFacturas() {
        //Obtener los clientes con más de 30 dias de mora
        String consulta = "select c from cliente c inner join contrato ct on c.id=ct.cliente_id " +
                "inner join tipo_contrato tc on ct.tipocontrato_id=tc.id group by c.id, ct.id, tc.id having " +
                "(current_date - ct.fecha_contrato) > 30";

        String consultaHQL = "FROM " + Cliente.class.getName() + " c inner join " + Contrato.class.getName()
                + " ct on c.id=ct.cliente_id inner join " + TipoContrato.class.getName()
                + " tc on ct.tipocontrato_id=tc.id group by c.id, ct.id, tc.id having " +
                "(current_date - ct.fecha_contrato) > 30";

        // Obtener la lista de clientes con base en la consulta
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil
                .getSessionFactory());
        try {
            ObservableList<Contrato> contratos = contratoService.getAll().stream().filter(contrato -> {
                LocalDate fechaContrato = contrato.getFecha_contrato();
                LocalDate fechaActual = LocalDate.now();
                return fechaActual.minusDays(30).isAfter(fechaContrato);
            }).collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);

            ObservableList<Cliente> clientes = FXCollections.observableArrayList();

            contratos.forEach(contrato -> {
                Cliente cliente = contrato.getCliente();
                if (!clientes.contains(cliente) && contrato.getTipocontrato().getServicio().getNombre().equals("BASICO")) {
                    clientes.add(cliente);
                }
            });

            // Obtener todas facturados
            ObservableList<Factura> facturas = FXCollections.observableArrayList();
            IGenericService<Factura> facturaServicio = new GenericServiceImpl<>(Factura.class, HibernateUtil
                    .getSessionFactory());
            facturas.addAll(facturaServicio.getAll());

            // Obtener clientes que ya han sido facturados
            ObservableList<Cliente> clientesFacturados = FXCollections.observableArrayList();
            facturas.forEach(factura -> {
                Cliente cliente = factura.getCliente();
                if (!clientesFacturados.contains(cliente) && factura.getEstado().equals("Pagada")) {
                    clientesFacturados.add(cliente);
                }
            });

            // Obtener clientes que no han sido facturados
            ObservableList<Cliente> clientesNoFacturados = FXCollections.observableArrayList();
            clientes.forEach(cliente -> {
                if (clientesFacturados.contains(cliente) && !clientesNoFacturados.contains(cliente) && clientes.contains(cliente)) {
                    clientesNoFacturados.add(cliente);
                }
            });

            //ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteService.consultarClientes(consultaHQL,null));
                clientesNoFacturados.forEach(cliente -> {
                    logger.info("Cliente: " + cliente);
                    // Crear la factura
                    Factura factura = new Factura();
                    factura.setUsuario(usuario);
                    factura.setCliente(cliente);
                    factura.setFecha_factura(LocalDate.now());
                    factura.setTotal(0f);
                    factura.setEstado("pendiente");

                    // Guardar la factura
                    IGenericService<Factura> facturaService = new GenericServiceImpl<>(Factura.class, HibernateUtil
                            .getSessionFactory());
                    facturaService.save(factura);

                    // Obtener factura creada
                    Factura facturaCreada = facturaService.getById(factura.getId());
                    LOGGER.info("Factura creada: " + facturaCreada);

                    //  Generar detalle de factura
                    IGenericService<DetalleFactura> detalleFacturaService = new GenericServiceImpl<>(DetalleFactura
                            .class,HibernateUtil.getSessionFactory());

                    DetalleFactura detalleFactura = new DetalleFactura();
                    detalleFactura.setFactura(facturaCreada);
                    detalleFactura.setServicio(obtenerServicioCliente(cliente, obtenerContratos()));
                    detalleFactura.setDescripcion("mensual");
                    detalleFactura.setTotal_pagar(servicioSeleccionado.getPrecio());

                    detalleFacturaService.save(detalleFactura);
            });
        } catch (Exception e) {
            logger.error("Error al obtener los clientes", e);
        }

    }

    //Obtener todos los contratos
    public ObservableList<Contrato> obtenerContratos() {
        IGenericService<Contrato> contratoService = new GenericServiceImpl<>(Contrato.class, HibernateUtil
                .getSessionFactory());
        return FXCollections.observableArrayList(contratoService.getAll());
    }

    public Servicio obtenerServicioCliente(Cliente cliente, ObservableList<Contrato> contratos) {
        Servicio servicio = null;
        for (Contrato contrato : contratos) {
            if (Objects.equals(contrato.getCliente().getId(), cliente.getId())) {
                servicio = contrato.getTipocontrato().getServicio();
                servicioSeleccionado = servicio;
                break;
            }
        }
        return servicio;
    }

    @FXML
    private void mostrarInicio() {

    }

    private void mostrarPanel() {
        btMostrar.setOnMouseClicked (event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(javafx.util.Duration.seconds(0.4));
            slide.setNode(panePrincipal);
            slide.setToX(-240);
            slide.play();
            panePrincipal.setTranslateX(0);
            slide.setOnFinished(event1 -> {
                btMostrar.setVisible(false);
                btOcultar.setVisible(true);
            });
        });
    }

    private void ocultarPanel(){
        btOcultar.setOnMouseClicked (event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(javafx.util.Duration.seconds(0.4));
            slide.setNode(panePrincipal);
            slide.setToX(0);
            slide.play();
            panePrincipal.setTranslateX(-240);
            slide.setOnFinished(event1 -> {
                btOcultar.setVisible(false);
                btMostrar.setVisible(true);
            });

        });
    }

    @Override
    public void start(Stage stage) {

    }

    public void mostrar_clientes() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Clientes.fxml"));
            Pane clientes = loader.load();
            panelPadre.setCenter(clientes);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_facturar() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Facturacion.fxml"));
            AnchorPane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    public void mostrar_gestiones() {
        try {
            FXMLLoader Loader = new FXMLLoader(getClass().getResource("/fxml/Configuraciones.fxml"));
            Pane configuraciones = Loader.load();
            panelPadre.setCenter(configuraciones);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_usuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegistrarUsuarios.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_contratos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Contratos.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_servicios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Servicio.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public void mostrar_reportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reportes.fxml"));
            Pane registrarse = loader.load();
            panelPadre.setCenter(registrarse);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
