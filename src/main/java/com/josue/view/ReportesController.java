package com.josue.view;

import com.josue.modelo.Pago;
import com.josue.reportes.Reportes;
import com.josue.service.GenericServiceImpl;
import com.josue.service.IGenericService;
import com.josue.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

public class ReportesController {
    // logger of Log4j
    private static final Logger log = LogManager.getLogger(ReportesController.class);
    public void mostrarProductos(ActionEvent actionEvent) throws Exception {
        var parametros = new HashMap<String, Object>();
        parametros.put("primer_nombre", "Victor");
        parametros.put("num_cedula", "61517109870001W");
        //Reportes.generarReporte("reportes/Productos.jrxml", parametros);
    }

    public void mostrarPagos(MouseEvent mouseEvent) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();

        IGenericService<Pago> pagoService = new GenericServiceImpl<>(Pago.class, HibernateUtil
                .getSessionFactory());
        ObservableList<Pago> pagos = FXCollections.observableArrayList(pagoService.getAll());
        log.info("Pagos: " + pagos);

        URL urlLogo = ReportesController.class.getClassLoader().getResource( "reportes/cablezel.png") ;
        BufferedImage urlImage = ImageIO.read(urlLogo);

        parametros.put("logo", urlImage);

        Reportes.generarReporte("reportes/Pagos.jrxml", parametros, new JRBeanCollectionDataSource(pagos));

    }
}
