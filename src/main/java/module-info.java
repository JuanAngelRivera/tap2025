module com.example.tap2025
{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;

    opens com.example.tap2025 to javafx.fxml;
    exports com.example.tap2025;
}