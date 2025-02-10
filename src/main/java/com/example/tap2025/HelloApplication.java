package com.example.tap2025;

import com.example.tap2025.vistas.Calculadora;
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
    MenuItem menu_item_calculadora;
    Button btn;

    private void create_ui()
    {
        menu_item_calculadora = new MenuItem("Calculadora");
        menu_competencia1 = new Menu("Competencia 1");
        menu_item_calculadora.setOnAction(event -> new Calculadora());
        menu_competencia1.getItems().addAll(menu_item_calculadora);
        menu_bar = new MenuBar();
        menu_bar.getMenus().addAll(menu_competencia1);
        vbox = new VBox(menu_bar);
        scene = new Scene(vbox);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        create_ui();
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }
}