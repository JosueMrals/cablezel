package com.josue.reportes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

//import javax.imageio.ImageIO;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Reportes {

    static JasperPrint jp;

    // Logger of Log4j
    private static Logger log = LogManager.getLogger(Reportes.class);

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

    static public void generarReporte(String ruta, HashMap<String, Object> parametros) throws Exception {
        try {

            // Load the compiled report from the .jasper file
            InputStream is = Reportes.class.getClassLoader().getResourceAsStream(ruta);
            JasperDesign jd = JRXmlLoader.load(is);
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            // Create a JasperPrint object
            jp = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            // Show the report
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
            jv.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jv.setTitle("Reporte");
            jv.requestFocus();
            jv.setVisible(true);
        } catch (JRException e) {
            log.error("Error al generar el reporte: " + ruta, e);
            throw e;
        }
    }




}
