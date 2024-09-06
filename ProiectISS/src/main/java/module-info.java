module com.example.proiectiss {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires ormlite.jdbc;

    opens com.example.proiectiss to javafx.fxml;
    exports com.example.proiectiss;

    exports com.example.proiectiss.model to ormlite.jdbc;
    opens com.example.proiectiss.model to ormlite.jdbc,javafx.fxml,javafx.base,javafx.graphics;
    opens com.example.proiectiss.repository to javafx.fxml;
    opens com.example.proiectiss.service to javafx.fxml;
    exports com.example.proiectiss.service;


//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;

//
//    opens com.example.proiectiss.model to javafx.fxml;
//    exports com.example.proiectiss.model;
}