package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class Rompecabezas extends Stage
{
    private Scene scene;
    private VBox vbox;
    private HBox hbox_menu, hbox_menu_left, hbox_menu_right, hbox_interface;
    private Pane pane_pieces, pane_board;
    private Button reset, change_difficulty;
    private Label best_time, timer;
    private ArrayList<ImageView> pieces;

    public void create_ui()
    {
        reset = new Button("Reiniciar");
        change_difficulty = new Button("Cambiar dificultad");
        change_difficulty.setOnAction(e -> create_menu_selection());
        hbox_menu_left = new HBox(reset, change_difficulty);
        hbox_menu_left.setSpacing(10);
        hbox_menu_left.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hbox_menu_left, Priority.ALWAYS);

        best_time = new Label("Mejor tiempo: --:--:--");
        timer = new Label("Contador: --:--:--");
        hbox_menu_right = new HBox(best_time, timer);
        hbox_menu_right.setSpacing(10);
        hbox_menu_right.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(hbox_menu_right, Priority.ALWAYS);

        hbox_menu = new HBox(hbox_menu_left, hbox_menu_right);
        hbox_menu.setPadding(new Insets(20, 20, 10, 20));
        hbox_menu.setSpacing(10);

        pane_pieces = new Pane();
        pane_pieces.setPrefSize(300, 1000);
        pane_pieces.setId("pane_pieces");
        pane_pieces.widthProperty().addListener((observable, oldValue, newValue) ->
                update_clip(pane_pieces));
        pane_pieces.heightProperty().addListener((observable, oldValue, newValue) ->
                update_clip(pane_pieces));
        pane_board = new Pane();
        pane_board.setId("pane_pieces");
        pane_board.setPrefSize(1000, 1000);

        hbox_interface = new HBox(pane_pieces, pane_board);
        hbox_interface.setSpacing(10);
        hbox_interface.setAlignment(Pos.CENTER);
        hbox_interface.setPadding(new Insets(10, 10, 30, 10));

        vbox = new VBox(hbox_menu, hbox_interface);
        scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        setTitle("Rompecabezas");
        setMaximized(true);

        Rectangle rectangle = new Rectangle(pane_pieces.getWidth(), pane_pieces.getHeight());
        pane_pieces.setClip(rectangle);
    }

    private void update_clip(Pane pane)
    {
        if (pane.getClip() == null)
        {
            Rectangle clip = new Rectangle(pane.getWidth(), pane.getHeight());
            pane.setClip(clip);
        }
        else
        {
            Rectangle clip = (Rectangle) pane.getClip();
            clip.setWidth(pane.getWidth());
            clip.setHeight(pane.getHeight());
        }
    }

    public void create_menu_selection()
    {
        Label difficulty = new Label("Dificultad");
        Button easy  = new Button("Fácil");
        easy.setPrefSize(120, 20);
        Button medium = new Button("Normal");
        medium.setPrefSize(120, 20);
        Button hard = new Button("Difícil");
        hard.setPrefSize(120, 20);
        VBox vbox_buttons = new VBox(difficulty, easy, medium, hard);
        vbox_buttons.setSpacing(10);
        vbox_buttons.setAlignment(Pos.CENTER_LEFT);

        Label best_time = new Label("Mejor tiempo");
        Label high_score_easy = new Label("21:09:24");
        Label high_score_medium = new Label("21:09:24");
        Label high_score_hard = new Label("21:09:24");
        VBox vbox_labels = new VBox(best_time, high_score_easy, high_score_medium, high_score_hard);
        vbox_labels.setSpacing(20);
        vbox_labels.setAlignment(Pos.TOP_RIGHT);

        HBox hbox = new HBox(vbox_buttons, vbox_labels);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        Scene menu_scene = new Scene(hbox);
        menu_scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        Stage menu_stage = new Stage();
        menu_stage.setScene(menu_scene);
        menu_stage.setTitle("Selección de dificultad");
        menu_stage.setResizable(false);
        easy.setOnAction(e -> { config(4); menu_stage.close(); });
        medium.setOnAction(e -> { config(9); menu_stage.close(); });
        hard.setOnAction(e -> { config(16); menu_stage.close(); });
        menu_stage.show();
    }

    private void config(int n)
    {
        create_pieces(n);
    }

    private void create_pieces(int n)
    {
        ArrayList<ImageView> pieces = new ArrayList<>();
        String direction = "/images/";
        int size = (int)(Math.pow(n, 0.5));
        switch (n)
        {
            case 4:
                direction += "puzzle_easy_mode/";
                break;
            case 9:
                direction += "puzzle_medium_mode/";
                break;
            case 16:
                direction += "puzzle_hard_mode/";
                break;
        }
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                String new_direction = direction +  "" + i + "-" + j + ".png";
                Piece piece = new Piece(new_direction, pane_pieces, pane_board);
                pieces.add(piece);
                piece.set_position(pane_pieces);
            }
        }
        show();
    }

    public Rompecabezas()
    {
        create_ui();
        create_menu_selection();
        setScene(scene);
    }
}

class Piece extends ImageView
{
    void set_position (Pane pane)
    {
        Random random = new Random();
        double x = random.nextDouble() * (pane.getWidth() - getFitWidth());
        double y = random.nextDouble() * (pane.getHeight() - getFitHeight());

        setLayoutX(x);
        setLayoutY(y);
        pane.getChildren().add(this);
    }

    private void set_draggable_piece(Pane pieces, Pane board)
    {
        double[] initial_position = new double[2];
        setOnMousePressed(e ->
        {
            initial_position[0] = e.getSceneX() - getLayoutX();
            initial_position[1] = e.getSceneY() - getLayoutY();
            Pane parent = (Pane) getParent();
            if(parent == pieces)
            {
                pieces.getChildren().remove(this);
                board.getChildren().add(this);
            }
        });
        setOnMouseDragged(e ->
        {
            double offset_x = e.getSceneX() - initial_position[0];
            double offset_y = e.getSceneY() - initial_position[1];
x|            setLayoutX(offset_x);
            setLayoutY(offset_y);
        });
    }

    Piece(String direction, Pane pieces, Pane board)
    {
        setImage(new Image(getClass().getResourceAsStream(direction)));
        set_draggable_piece(pieces, board);
    }
}
