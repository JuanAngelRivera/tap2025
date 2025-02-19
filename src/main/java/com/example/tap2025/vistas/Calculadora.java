package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculadora extends Stage
    {
        private TextField text_field;
        private VBox vbox;
        private GridPane grid_pane;
        private Button [][] keypad;
        private Scene scene;
        private String [] button_text;
        private String operator;
        private double left, right;

        public void create_ui() {
            button_text = new String[]{"7", "8", "9", "÷", "4", "5", "6", "x", "1", "2", "3"
                    , "+", ".", "0", "-", "="};
            left = right = 0;
            operator = "";
            create_keypad();
            text_field = new TextField("0");
            text_field.setEditable(false);
            text_field.setPrefSize(200, 60);
            text_field.setAlignment(Pos.BASELINE_RIGHT);
            vbox = new VBox(text_field, grid_pane);
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(5));
            scene = new Scene(vbox);
            scene.getStylesheets().add(getClass().getResource("/styles/calculadora.css").toExternalForm());
            this.setTitle("Calculadora");
            this.setResizable(false);
            this.show();
        }

        public void create_keypad()
        {
            grid_pane = new GridPane();
            keypad = new Button[4][4];
            grid_pane.setHgap(2);
            grid_pane.setVgap(3);

            for (int i = 0; i < 4; i++)//columna
            {
                for (int j = 0; j < 4; j++)//renglon
                {
                    keypad[i][j] = new Button(button_text[j * 4 + i]);
                    select_button_style(keypad[i][j]);
                    int ifinal = i;
                    int jfinal = j;
                    keypad[i][j].setOnAction(event -> pressed_button(button_text[jfinal * 4 + ifinal]));
                    grid_pane.add(keypad[i][j], i, j);
                }
            }
        }

        private void select_button_style(Button button)
        {
            if(button.getText().equals("="))
                button.setId("result-button");
            else if(button.getText().equals("+") || button.getText().equals("-") ||
                    button.getText().equals("x") || button.getText().equals("÷") || button.getText().equals("."))
                button.setId("operation-button");
            button.setPrefSize(80, 50);
        }

        public void pressed_button(String value)
        {
            if (text_field.getAlignment() == Pos.BASELINE_LEFT)
            {
                text_field.setId("text-field");
                text_field.setText("0");
                text_field.setAlignment(Pos.BASELINE_RIGHT);
            }
            switch (value)
            {
                case "=":
                    System.out.println("SE PULSO EL IGUAL");
                    if (!operator.equals(""))
                    {
                        right = Double.parseDouble(text_field.getText());
                        result();
                    }
                    break;
                case ".":
                    if(!text_field.getText().contains("."))
                        text_field.appendText(value);
                    break;
                default:
                    if (value.equals("+") || value.equals("-") || value.equals("x") || value.equals("÷"))
                    {//si se presiona un simbolo de operacion
                        System.out.println("SE PULSO SIMBOLO");

                        if(text_field.getText().equals("0") && left == 0 && operator.equals("") && value.equals("-"))
                            text_field.setText(value);

                        if(operator.equals("") && !text_field.getText().equals("-"))//no hay aun simbolo de operacion
                        {
                            if (left == 0)
                                left = Double.parseDouble(text_field.getText());
                            text_field.setText("0");
                            operator = value;
                        }
                    }
                    else
                    {
                        System.out.println("SE PULSO NUMERO O PUNTO");
                        if(operator.equals("") && left != 0)//hay resultado en pantalla pero se introdujo
                        {// otro numero
                            left = 0;
                            text_field.setText("0");
                        }

                        if(text_field.getText().equals("0") && !value.equals("."))
                            text_field.setText(value);
                        else
                            text_field.appendText(value);
                    }
                    break;
            }
        }

        public void result()
        {
            System.out.println("ENTRO A RESULT");
            System.out.println("Operacion: " + left + " " + operator + " " + right);
            String exception = "";
            double result = 0;

            switch (operator)
            {
                case "+":
                    result = left + right;
                    break;
                case "-":
                    result = left - right;
                    break;
                case "x":
                    result = left * right;
                    break;
                case "÷":
                    if (right == 0)
                    {
                        System.out.println("DIVISION ENTRE CERO");
                        exception = "No se puede dividir entre cero";
                    }
                    else
                        result = left / right;
                    break;
                default:
                    System.out.println("Se trató de hacer operación sin ningun simbolo");
                    break;
            }
            left = result;
            right = 0;
            operator = "";
            System.out.println("DATOS AL FINAL: resultado = " + result + " operador " + operator + "  izquierda "
                    + left + " derecha " + right);
            if (result % 1 == 0)
            {
                long result_long = Math.round(result);
                text_field.setText(result_long + "");
            }
            else
            {
                String result_string = String.valueOf(result);
                String decimal_format = "#.";
                int excess = result_string.length() - 14;
                System.out.println("Tamaño del resultado = " + result_string.length());
                for (int i = 0; i < 14 - excess; i++)
                {
                    decimal_format += "#";
                }
                System.out.println("El decimal format quedó como: " + decimal_format);
                DecimalFormat df = new DecimalFormat(decimal_format);
                text_field.setText(String.valueOf(df.format(result)));
            }
            System.out.println(exception);
            if (!exception.equals(""))
            {
                text_field.setText(exception);
                text_field.setId("text-field-error");
                text_field.setAlignment(Pos.BASELINE_LEFT);
            }
        }

        public Calculadora()
        {
            create_ui();
            setScene(scene);
        }
    }