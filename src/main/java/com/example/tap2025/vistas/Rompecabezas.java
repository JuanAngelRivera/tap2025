package com.example.tap2025.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Rompecabezas extends Stage
{
    private Scene scene;
    private VBox vbox;
    private HBox hbox_menu, hbox_menu_left, hbox_menu_right,hbox_board;
    private Pane pane_pieces;
    private Button reset, change_difficulty;
    private Label best_time, timer, final_time;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private int placed_pieces, seconds, miliseconds, minutes;
    private Timeline timeline;
    private boolean timer_started;
    private Stage win_menu, mode_menu;

    public void create_ui()
    {
        reset = new Button("Reiniciar");
        reset.setOnAction(e -> reset());
        change_difficulty = new Button("Cambiar dificultad");
        change_difficulty.setOnAction(e ->
        {
            stop_time();
            mode_menu.show();
        });
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
        hbox_menu.setSpacing(10);

        pane_pieces = new Pane();
        pane_pieces.setPrefSize(1000, 1000);
        pane_pieces.setId("pane-pieces");

        hbox_board = new HBox(pane_pieces);

        vbox = new VBox(hbox_menu, hbox_board);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setSpacing(10);
        scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        setTitle("Rompecabezas");
        setMaximized(true);
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
        mode_menu = new Stage();
        mode_menu.setScene(menu_scene);
        mode_menu.setTitle("Selección de dificultad");
        mode_menu.setResizable(false);
        easy.setOnAction(e -> config(4));
        medium.setOnAction(e -> config(9));
        hard.setOnAction(e -> config(16));
        mode_menu.show();
    }

    public void win_menu()
    {
        Label label = new Label("FELICIDADES! COMPLETASTE EL ROMPECABEZAS");
        reset = new Button("Reiniciar");
        reset.setOnAction(e -> reset());
        change_difficulty = new Button("Cambiar dificultad");
        change_difficulty.setOnAction(e -> create_menu_selection());
        HBox hbox = new HBox(reset, change_difficulty);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        final_time = new Label("Contador: --:--:--");
        VBox vbox = new VBox(label, final_time, hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        win_menu = new Stage();
        win_menu.setScene(scene);
        win_menu.setTitle("GANASTE");
        win_menu.setResizable(false);
    }

    private void config(int n)
    {
        if(pieces.size() == n)
            reset();
        else
            create_pieces(n);
        mode_menu.hide();
    }

    private void create_pieces(int n)
    {
        timer_started = false;
        placed_pieces = seconds = miliseconds = minutes = 0;
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
        Piece config = new Piece(direction +  "0-0.png", null);
        board_config(size, config);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                String new_direction = direction +  "" + i + "-" + j + ".png";
                Piece piece = new Piece(new_direction, this::check_completed);
                piece.correct_x = (i * piece.getImage().getWidth());
                piece.correct_y = (j * piece.getImage().getHeight());
                pieces.add(piece);
                //System.out.println("Pieza " + i + " " + j + " x correcta " + piece.correct_x + " y correcta " + piece.correct_y );
            }
        }
        for (Piece piece :  pieces)
        {
            piece.set_position(pane_pieces);
        }
        show();
        start_time();
    }

    void board_config(int size, ImageView piece)
    {
        pane_pieces.setPrefSize(piece.getImage().getWidth() * size, piece.getImage().getHeight() * size);
        hbox_board.setAlignment(Pos.CENTER);
        hbox_board.setPadding(new Insets(100, 0, 0 ,0));
    }

    void check_completed()
    {
        placed_pieces++;
        if (placed_pieces == pieces.size())
        {
            stop_time();
            win_menu.show();
        }
    }

    void reset()
    {
        stop_time();
        int total_pieces = pieces.size();
        pane_pieces.getChildren().clear();
        pieces.clear();
        create_pieces(total_pieces);

        if(win_menu.isShowing())
            win_menu.hide();
    }

    void start_time()
    {
        if(timer_started) return;
        timer_started = true;

        timeline = new Timeline(new KeyFrame(Duration.millis(1), e ->
        {
            miliseconds++;
            if (miliseconds == 1000)
            {
                seconds++;
                miliseconds = 0;
            }
            if(seconds == 60)
            {
                seconds = 0;
                minutes++;
            }
            String text = "Tu tiempo: " + (minutes == 0 ? "0:" : minutes + ":") + (seconds == 0 ? "0:" : seconds + ":") +
                    (miliseconds == 0 ? "0-:" : miliseconds);
            timer.setText(text);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stop_time()
    {
        if (timeline != null)
        {
            timeline.stop();
            final_time.setText(timer.getText());
        }
    }

    public Rompecabezas()
    {
        create_ui();
        create_menu_selection();
        win_menu();
        setScene(scene);
    }
}

class Piece extends ImageView
{
    public double correct_x;
    public double correct_y;
    private Runnable on_piece_placed;
    void set_position (Pane pane)
    {
        Random random = new Random();
        double x = random.nextDouble() * (getImage().getWidth());
        double y = random.nextDouble() * (getImage().getHeight());
        pane.getChildren().add(this);

        setLayoutX(x);
        setLayoutY(y);
    }

    private void set_draggable_piece()
    {
        double[] initial_position = new double[2];
        setOnMousePressed(e ->
        {
                initial_position[0] = e.getSceneX() - getLayoutX();
                initial_position[1] = e.getSceneY() - getLayoutY();

        });
        setOnMouseDragged(e ->
        {
                double offset_x = e.getSceneX() - initial_position[0];
                double offset_y = e.getSceneY() - initial_position[1];
                setLayoutX(offset_x);
                setLayoutY(offset_y);
        });
        setOnMouseReleased(e ->
        {
            double tolerance = getImage().getWidth() / 3;
            if(Math.abs(getLayoutX() - correct_x) <= tolerance && Math.abs(getLayoutY() - correct_y) <= tolerance)
            {
                setLayoutX(correct_x);
                setLayoutY(correct_y);
                disable_drag();
                if(on_piece_placed != null)
                    on_piece_placed.run();
                toBack();
            }
        });
    }

    private void disable_drag()
    {
        setOnMousePressed(null);
        setOnMouseDragged(null);
        setOnMouseReleased(null);
    }

    public boolean placed_correctly()
    {
        return (Math.abs(getLayoutX()) == correct_x && Math.abs(getLayoutY()) == correct_y);
    }

    Piece(String direction, Runnable on_piece_placed)
    {
        setImage(new Image(getClass().getResourceAsStream(direction)));
        set_draggable_piece();
        this.on_piece_placed = on_piece_placed;
    }
}
