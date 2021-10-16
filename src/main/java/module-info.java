module com.josue.cablezelmnv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires java.naming;

    opens com.josue.cablezelmnv to javafx.fxml,javafx.graphics;
    opens com.josue.view to javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.base;
    requires org.hibernate.orm.core;
    requires java.persistence;
}
