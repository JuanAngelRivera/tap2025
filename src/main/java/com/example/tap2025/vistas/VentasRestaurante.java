package com.example.tap2025.vistas;

import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import javafx.scene.Scene;

public class VentasRestaurante extends Stage
{
    private Panel panel_restaurante;
    private Scene scene;

    public VentasRestaurante()
    {
        create_ui();
        this.setTitle("Fondita Doña Lupe");
        this.setScene(scene);
        this.show();
    }

    void create_ui()
    {
        panel_restaurante = new Panel("Tacos el Inge");
        panel_restaurante.getStyleClass().add("panel-primary");
        scene = new Scene(panel_restaurante, 300, 200);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
}
