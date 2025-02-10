package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Calculadora extends Stage
    {
        private TextField text_field;
        private VBox vbox;
        private GridPane grid_pane;
        private Button [][] keypad;
        private Scene scene;
        private String [] button_text;
        public void create_ui()
        {
            button_text = new String[]{"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3"
            , "+", "=", "0", ".", "-"};
            create_keypad();
            text_field = new TextField("0");
            text_field.setEditable(false);
            text_field.setAlignment(Pos.BASELINE_RIGHT);
            vbox = new VBox(text_field, grid_pane);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(10));
            scene = new Scene(vbox);
            this.setTitle("Calculadora");
            this.show();
        }

        public void create_keypad()
        {
            grid_pane = new GridPane();
            keypad = new Button[4][4];
            grid_pane.setHgap(10);
            grid_pane.setVgap(10);

            for (int i = 0; i < 4; i++)//columna
            {
                for (int j = 0; j < 4; j++)//renglon
                {
                    keypad[i][j] = new Button(button_text[j * 4 + i]);
                    keypad[i][j].setPrefSize(50, 50);
                    grid_pane.add(keypad[i][j], i, j);
                }
            }
        }

        public Calculadora()
        {
            create_ui();
            setScene(scene);
        }
    }