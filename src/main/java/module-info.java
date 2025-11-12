module com.project.elcincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project.elcincuentazo to javafx.graphics;
    opens com.project.elcincuentazo.controller to javafx.fxml;
    opens com.project.elcincuentazo.models to javafx.base;

    exports com.project.elcincuentazo;
}
