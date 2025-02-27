package com.example.tap2025.vistas;

import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaClientes extends Stage
{
    private ToolBar tlbMenu;
    private TableView <ClientesDAO> tbvClientes;
    private VBox vbox;
    private Scene escena;
    private Button btnAgregar;

    public ListaClientes()
    {
        crearUI();
        setTitle("Lista de Clientes");
        setScene(escena);
        show();
    }

    private void crearUI()
    {
        btnAgregar = new Button();
        btnAgregar.setGraphic(new ImageView(getClass().getResource("/images/restaurante/add_icon.png").toExternalForm()));
        tlbMenu = new ToolBar(btnAgregar);
        tbvClientes = new TableView<>();
        vbox = new VBox(tlbMenu, tbvClientes);
        escena = new Scene(vbox, 800, 600);
    }
}
