module com.aulkhami.pakupos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.aulkhami.pakupos to javafx.fxml;
    opens com.aulkhami.pakupos.controllers to javafx.fxml;
    opens com.aulkhami.pakupos.models.entities to javafx.fxml;
    opens com.aulkhami.pakupos.views to javafx.fxml;
    opens com.aulkhami.pakupos.views.components to javafx.fxml;

    exports com.aulkhami.pakupos;
    exports com.aulkhami.pakupos.controllers;
    exports com.aulkhami.pakupos.models.entities;
}
