package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ClientesDAO
{
    private int idCte;
    private String nomCte;
    private String telCte;
    private String direccion;
    private String emailCte;

    public int getIdCte()
    {
        return idCte;
    }

    public void setIdCte(int idCte)
    {
        this.idCte = idCte;
    }

    public String getNomCte()
    {
        return nomCte;
    }

    public void setNomCte(String nomCte)
    {
        this.nomCte = nomCte;
    }

    public String getTelCte()
    {
        return telCte;
    }

    public void setTelCte(String telCte)
    {
        this.telCte = telCte;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String getEmailCte()
    {
        return emailCte;
    }

    public void setEmailCte(String emailCte)
    {
        this.emailCte = emailCte;
    }

    public void INSERT()
    {
        String query = "INSERT INTO clientes(nomCte, telCte, direccion, emailCte) VALUES( '" + nomCte + "', '" + telCte + "', '" +
                direccion + "', '" + emailCte + "');";
        try
        {
            Statement statement = Conexion.connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void UPDATE()
    {
        String query = "UPDATE clientes SET nomCTE = '" + nomCte + "',telCte = '" + telCte + "',direccion = '" + direccion +
                "',emailCte = '" + emailCte + "' WHERE idCte = " + idCte + ";";//dado que id es numerico no hacen falta las comillas
        try
        {
            Statement statement = Conexion.connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void DELETE()
    {
        String query = "DELETE FROM clientes WHERE idCte = " + idCte + ";";
        try
        {
            Statement statement = Conexion.connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

        public ObservableList<ClientesDAO> SELECT()
    {
        String query = "SELECT * FROM clientes;";
        ObservableList<ClientesDAO> listaC = FXCollections.observableArrayList();
        ClientesDAO objC;
        try
        {
            Statement statement = Conexion.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                objC = new ClientesDAO();
                objC.setIdCte(resultSet.getInt("idCte"));
                objC.setNomCte(resultSet.getString("nomCte"));
                objC.setTelCte(resultSet.getString("telCte"));
                objC.setDireccion(resultSet.getString("direccion"));
                objC.setEmailCte(resultSet.getString("emailCte"));
                listaC.add(objC);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return listaC;
    }
}
