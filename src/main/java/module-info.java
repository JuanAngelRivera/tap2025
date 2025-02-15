module com.example.tap2025 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tap2025 to javafx.fxml;
    exports com.example.tap2025;
}