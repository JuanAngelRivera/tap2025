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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rompecabezas extends Stage
{
    private Scene scene;
    private VBox vbox, vbox_win_menu;
    private HBox hbox_menu, hbox_menu_left, hbox_menu_right,hbox_board;
    private Pane pane_pieces;
    private Button reset, change_difficulty;
    private Label best_time, timer, final_time;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private int placed_pieces, seconds, miliseconds, minutes;
    private Timeline timeline;
    private boolean timer_started;
    private Stage win_menu, mode_menu;
    final int string_size = 9;
    private String archive = "scores.oveja", hard_hs, easy_hs, medium_hs;

    public void create_ui()
        {
        reset = new Button("Reiniciar");
        reset.setOnAction(e -> reset());
        change_difficulty = new Button("Cambiar dificultad");
        change_difficulty.setOnAction(e ->
        {
            timeline.stop();
            update_score();
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
        setResizable(false);
    }

    public void create_menu_selection()
    {
        hard_hs = read_highest_score(archive, 2);
        easy_hs = read_highest_score(archive, 0);
        medium_hs = read_highest_score(archive, 1);
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
        Label high_score_easy = new Label((easy_hs == null || easy_hs.isEmpty()) ? "--:--:--" : easy_hs);
        Label high_score_medium = new Label((medium_hs == null  || medium_hs.isEmpty()) ? "--:--:--" : medium_hs);
        Label high_score_hard = new Label((hard_hs == null || hard_hs.isEmpty())? "--:--:--" : hard_hs);
        VBox vbox_labels = new VBox(best_time, high_score_easy, high_score_medium, high_score_hard);
        vbox_labels.setSpacing(20);
        vbox_labels.setAlignment(Pos.TOP_RIGHT);

        Label all_scores = new Label("Todas las puntuaciones");
        Button easy_scores = new Button("Fácil");
        easy_scores.setPrefSize(120, 20);
        Button medium_scores = new Button("Normal");
        medium_scores.setPrefSize(120, 20);
        Button hard_scores = new Button("Difícil");
        hard_scores.setPrefSize(120, 20);
        VBox vbox_scores = new VBox(all_scores, easy_scores, medium_scores, hard_scores);
        vbox_scores.setSpacing(20);
        vbox_scores.setAlignment(Pos.CENTER_RIGHT);

        HBox hbox = new HBox(vbox_buttons, vbox_labels, vbox_scores);
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
        easy_scores.setOnAction(e -> score_menu(4));
        medium_scores.setOnAction(e -> score_menu(9));
        hard_scores.setOnAction(e -> score_menu(16));
        mode_menu.show();
        mode_menu.setOnHidden(e ->
        {
            if (placed_pieces != pieces.size())
                timeline.play();
        });
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
        vbox_win_menu = new VBox(label, final_time, hbox);
        vbox_win_menu.setPadding(new Insets(10, 10, 10, 10));
        vbox_win_menu.setSpacing(10);
        vbox_win_menu.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox_win_menu);
        scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        win_menu = new Stage();
        win_menu.setScene(scene);
        win_menu.setTitle("GANASTE");
        win_menu.setResizable(false);
    }

    public void score_menu(int mode)
    {
        List<String> registers = get_score_registers(mode);
        VBox vbox = new VBox();
        String title = "Ultimos tiempos para la dificultad " + get_difficulty(mode);
        Label Title = new Label(title);
        vbox.getChildren().add(Title);
        for (int i = 0; i < registers.size(); i++)
        {
            Label label = new Label("- " + registers.get(i));
            vbox.getChildren().add(label);
        }
        if (registers.isEmpty())
            vbox.getChildren().add(new Label("No hay ningun registro aun!"));
        vbox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/styles/rompecabezas.css").toExternalForm());
        Stage score_menu = new Stage();
        score_menu.setScene(scene);
        score_menu.setTitle(title);
        score_menu.show();
    }

    public List<String> get_score_registers(int mode)
    {
        String archive  = get_archive(mode);
        List<String> registers = new ArrayList<String>();
        try
        {
            RandomAccessFile raf = new RandomAccessFile(archive , "r");
            byte[] buffer = new byte[string_size];
            while(raf.read(buffer) == string_size)
            {
                String register = new String(buffer);
                registers.add(register);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return registers;
    }

    private void config(int n)
    {
        if(pieces.size() == n)
            reset();
        else
        {
            String best_score = read_highest_score(archive, (int)(Math.sqrt(n) - 2));
            create_pieces(n);
            best_time.setText("Mejor tiempo: " + (best_score == null ? "--:--:--" : best_score));
        }
        win_menu.hide();
        mode_menu.hide();
    }

    private void create_pieces(int n)
    {
        if(!pieces.isEmpty())
        {
            pieces.clear();
            pane_pieces.getChildren().clear();
        }

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
        board_config(size, new Piece(direction +  "0-0.png", null, size));
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                String new_direction = direction +  "" + i + "-" + j + ".png";
                Piece piece = new Piece(new_direction, this::check_completed, size);
                piece.correct_x = (i * piece.getImage().getWidth());
                piece.correct_y = (j * piece.getImage().getHeight());
                pieces.add(piece);
            }
        }
        for (Piece piece :  pieces)
        {
            piece.set_position(pane_pieces, size);
        }
        show();
        start_time();
    }

    void board_config(int size, ImageView piece)
    {
        pane_pieces.setPrefSize(piece.getImage().getWidth() * size, piece.getImage().getHeight() * size);
        hbox_board.setAlignment(Pos.CENTER);
        hbox_board.setPadding( new Insets( piece.getImage().getHeight() /size, 0, 0, 0));
    }

    void check_completed()
    {
        placed_pieces++;
        if (placed_pieces == pieces.size())
        {
            stop_time();
            int position = (int) (Math.sqrt(pieces.size()) - 2);
            String recorded_time = read_highest_score(archive, position);
            String new_time = timer.getText().split(" ")[1];
            update_score();
            if (recorded_time == null || recorded_time.equals("") || new_time.compareTo(recorded_time) < 0)
            {
                write_highest_score(archive, position);
                best_time.setText("Mejor tiempo: " + read_highest_score(archive, position));
                final_time.setText("Nuevo récord: " + read_highest_score(archive, position));
            }
            String archive = get_archive(pieces.size());
            write(archive);
            win_menu.show();
        }
    }

    void update_score()
    {
        easy_hs = read_highest_score(archive, 0);
        medium_hs = read_highest_score(archive, 1);
        hard_hs = read_highest_score(archive, 2);
    }

    void write_highest_score(String archive, int index)
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(archive, "rw");
            {
                int postion = index * string_size;
                raf.seek(postion);
                String text = timer.getText().split(" ")[1];
                raf.write(text.getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void write(String archive)
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(archive, "rw");
            {
                raf.seek(raf.length());
                String text = timer.getText().split(" ")[1];
                raf.write(text.getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    String read_highest_score(String archive, int index)
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(archive, "r");
            int position = index * string_size;
            raf.seek(position);
            byte[] buffer = new byte[string_size];
            raf.read(buffer);
            return new String(buffer, StandardCharsets.UTF_8).trim();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
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
            String text = String.format("Contador: %02d:%02d:%03d", minutes, seconds, miliseconds);
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

    String get_archive(int mode)
    {
        switch (mode)
        {
            case 4:
                return "easy_scores.oveja";
            case 9:
                return "medium_scores.oveja";
            case 16:
                return "hard_scores.oveja";
            default:
                return null;
        }
    }

    String get_difficulty(int mode)
    {
        switch (mode)
        {
            case 4:
                return "fácil";
            case 9:
                return "medium";
            case 16:
                return "hard";
        }
        return null;
    }

    public Rompecabezas()
    {
        try
        {
            win_menu();
            create_ui();
            create_menu_selection();

            setScene(scene);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Piece extends ImageView
{
    public double correct_x;
    public double correct_y;
    private Runnable on_piece_placed;

    void set_position (Pane pane, int size)
    {
        double factor = 0.7;
        Random random = new Random();
        double x = random.nextDouble() * getImage().getWidth() * size * factor;
        double y = random.nextDouble() * getImage().getHeight() * size * factor;
        pane.getChildren().add(this);

        setLayoutX(x);
        setLayoutY(y);
    }

    private void set_draggable_piece(int size)
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

                if(e.getSceneX() < getImage().getWidth() / 5)
                    setLayoutX(e.getSceneX() - getImage().getWidth());
                if(e.getSceneX() > getScene().getWidth() - getImage().getWidth() / 5)
                    setLayoutX(getImage().getWidth() * size);

                if(e.getSceneY() < getImage().getHeight() / 5)
                    setLayoutY(e.getSceneY() - getImage().getHeight() / size);
                if(e.getSceneY() > getScene().getHeight() - getImage().getHeight() / 5)
                    setLayoutY((getScene().getHeight() - 2 * getImage().getHeight()));

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

    Piece(String direction, Runnable on_piece_placed, int size)
    {
        setImage(new Image(getClass().getResourceAsStream(direction)));
        set_draggable_piece(size);
        this.on_piece_placed = on_piece_placed;
        setPickOnBounds(false);
    }
}
