module com.example.tap2025
{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires java.management;
    requires java.desktop;

    opens com.example.tap2025 to javafx.fxml;
    opens src.com.restaurante to javafx.fxml;
    exports src.com.restaurante.modelos;
    exports  src.com.restaurante.vistas;
    exports  src.com.restaurante.componentes;
    exports com.example.tap2025;
}