package com.example.tap2025;

import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.vistas.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application
{
    Scene scene;
    VBox vbox;
    Menu menu_competencia1;
    MenuBar menu_bar;
    MenuItem menu_item_calculadora, menu_item_restaurante;
    Button btn;

    private void create_ui()
    {
        menu_item_calculadora = new MenuItem("Calculadora");
        menu_item_restaurante = new MenuItem("Restaurante");
        menu_competencia1 = new Menu("Competencia 1");
        menu_item_calculadora.setOnAction(event -> new Calculadora());
        menu_item_restaurante.setOnAction(event -> new VentasRestaurante());
        menu_competencia1.getItems().addAll(menu_item_calculadora, menu_item_restaurante);
        menu_bar = new MenuBar();
        menu_bar.getMenus().addAll(menu_competencia1);
        vbox = new VBox(menu_bar);
        scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

    }

    @Override
    public void start(Stage stage) throws IOException
    {
        Conexion.create_connection();
        create_ui();
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }
}