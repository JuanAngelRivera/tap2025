package com.example.tap2025.modelos;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion
{
    static private String DB = "restaurante";
    static private String USER = "adminRestaurante";
    static private String PASSWORD = "admin2124";
    static private String HOST = "localhost";
    static private String PORT = "3306";
    public static Connection connection;

    public static void create_connection()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PASSWORD);
            System.out.println("CONEXION ESTABLECIDA ");
        }
        catch(Exception e)
        {
            System.out.println("OCURRIO UN ERROR:" + e.toString());
            e.printStackTrace();
        }
    }
}
