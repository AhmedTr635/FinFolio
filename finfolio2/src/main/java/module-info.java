module finfolio {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    opens com.example.finfolio to javafx.fxml;
    exports com.example.finfolio;
    exports com.example.finfolio.UsrController;
    opens com.example.finfolio.UsrController to javafx.fxml;
    exports com.example.finfolio.Admin;
    opens com.example.finfolio.Admin to javafx.fxml;

    opens com.example.finfolio.User to javafx.fxml;
    exports com.example.finfolio.Service;
    opens com.example.finfolio.Service to javafx.fxml;
    exports com.example.finfolio.Entite;
    opens com.example.finfolio.Entite to javafx.fxml;
    exports com.example.finfolio.util;
    opens com.example.finfolio.util to javafx.fxml;
}
