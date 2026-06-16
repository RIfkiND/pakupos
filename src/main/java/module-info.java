open module com.aulkhami.pakupos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires io.github.cdimascio.dotenv.java;

    exports com.aulkhami.pakupos;
    exports com.aulkhami.pakupos.app;
    exports com.aulkhami.pakupos.models.entities;
}
