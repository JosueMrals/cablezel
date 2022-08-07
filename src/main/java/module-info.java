module com.josue.cablezelmnv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires java.naming;

    opens com.josue.cablezelmnv to javafx.fxml, javafx.graphics;
    opens com.josue.view to javafx.fxml;
    opens com.josue.modelo to org.hibernate.orm.core;
    opens com.josue.reportes to jasperreports;
    requires javafx.graphicsEmpty;
    requires java.base;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jasperreports;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.desktop;

}
