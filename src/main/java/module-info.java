module com.example.nosqlproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires cassandra.driver.core;
    requires org.jsoup;

    opens com.example.nosqlproject to javafx.fxml;
    exports com.example.nosqlproject;
}