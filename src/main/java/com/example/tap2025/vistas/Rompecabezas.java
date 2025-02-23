package com.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Rompecabezas extends Stage
{
    private Scene scene;
    private VBox vbox;
    private HBox hbox_menu;
    private HBox hbox_interface;
    private Button reset;
    private Button change_difficulty;
    private Label best_time;
    private Label timer;

    public void create_ui()
    {
        reset = new Button("Reiniciar");
        change_difficulty = new Button("Cambiar dificultad");
        change_difficulty.setOnAction(e -> create_menu_selection());
        best_time = new Label("Mejor tiempo: --:--:--");
        timer = new Label("Contador: --:--:--");
        hbox_menu = new HBox(reset, change_difficulty, best_time, timer);
        hbox_interface = new HBox();
        vbox = new VBox(hbox_menu, hbox_interface);
        scene = new Scene(vbox);
        setTitle("Rompecabezas");
        show();
        this.setMaximized(true);
    }

    public void create_menu_selection()
    {
        Label difficulty = new Label("Dificultad");
        Label best_time = new Label("Mejor tiempo");
        HBox title = new HBox(difficulty, best_time);
        Button easy  = new Button("Fácil");
        Label high_score_easy = new Label("--:--:--");
        HBox hbox_easy = new HBox(easy, high_score_easy);
        Button medium = new Button("Normal");
        Label high_score_medium = new Label("--:--:--");
        HBox hbox_medium = new HBox(medium, high_score_medium);
        Button hard = new Button("Difícil");
        Label high_score_hard = new Label("--:--:--");
        HBox hbox_hard = new HBox(hard, high_score_hard);
        VBox vbox = new VBox(title, hbox_easy, hbox_medium, hbox_hard);
        Scene menu_scene = new Scene(vbox, 500, 200);
        Stage menu_stage = new Stage();
        menu_stage.setScene(menu_scene);
        menu_stage.setTitle("Selección de dificultad");
        menu_stage.show();
    }

    public Rompecabezas()
    {
        create_ui();
        setScene(scene);
        create_menu_selection();
    }
}
