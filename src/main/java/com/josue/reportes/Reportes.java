package com.josue.reportes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

//import javax.imageio.ImageIO;


import com.josue.util.HibernateUtil;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.jdbc.Work;


public class Reportes {
    // logger of Log4j
    private static final Logger log = LogManager.getLogger(Reportes.class);
    static JasperPrint jp;

    /**
     * @param url
     * @throws JRException
     * @throws IOException
     * @throws JRException
     * To generate a PDF report from a jrxml file
     */
    private static InputStream loadReport(URL url) throws Exception {
        FileInputStream is = readFile(url);
        if (is == null) {
            throw new Exception("Plantilla de reporte no encontrada. " + url);
        }
        return is;
    }

    /**
     * Reads a file from a URL
     * @param url
     * @return
     * @throws Exception
     */
    private static FileInputStream readFile(URL url) throws Exception {
        FileInputStream is = null;
        try {
            is = new FileInputStream(url.getFile());
        } catch (Exception e) {
            log.error("Error al cargar el archivo de reporte: " + url, e);
            throw e;
        }
        return is;
    }

    static public void generarReporte(String ruta, HashMap<String, Object> parametros, JRBeanCollectionDataSource datos) throws Exception {
        try {

            // obtener la conexion de la sesion hibernate
            HibernateEntityManager em = (HibernateEntityManager) HibernateUtil.getSessionFactory().createEntityManager();
            Session session = em.getSession();
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    try {
                        // cargar el archivo jrxml
                        InputStream is = Reportes.class.getClassLoader().getResourceAsStream(ruta);
                        JasperDesign jasperDesign = JRXmlLoader.load(is);
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                        // llenar el reporte con los datos
                        jp = JasperFillManager.fillReport(jasperReport, parametros, connection);
                        // mostrar el reporte
                        JasperViewer jv = new JasperViewer(jp, false);
                        jv.setVisible(true);
                    } catch (Exception e) {
                        log.error("Error al generar el reporte", e);
                    }
                }
            });

            // Load the compiled report from the .jasper file
            /*InputStream is = Reportes.class.getClassLoader().getResourceAsStream(ruta);
            JasperDesign jd = JRXmlLoader.load(is);
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            log.info("Reporte cargado: " + ruta);
            log.info("Parametros: " + parametros);
            log.info("Datos: " + datos);
            // Create a JasperPrint object
            jp = JasperFillManager.fillReport(jasperReport, parametros, datos);
            // Show the report
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
            jv.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jv.setTitle("Reporte");
            jv.requestFocus();
            jv.setVisible(true);*/
        } catch (Exception e) {
            log.error("Error al generar el reporte: " + ruta, e);
            throw e;
        }
    }




}
