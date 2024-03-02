module com.example.gestioncredit1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kernel;
    requires layout;
    requires javax.servlet.api;
    requires com.jfoenix;
    requires java.datatransfer;
    requires emoji.java;
    requires java.desktop;

    opens Entity to javafx.base;
    opens com.example.gestioncredit1 to javafx.fxml;

    // Export the com.example.gestioncredit1.controller package
    exports com.example.gestioncredit1.server;
    exports com.example.gestioncredit1.controller;
}
