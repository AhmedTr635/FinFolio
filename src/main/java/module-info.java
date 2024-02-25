module com.example.gestioncredit1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Entity to javafx.base;
    opens com.example.gestioncredit1 to javafx.fxml;
    exports com.example.gestioncredit1;

}