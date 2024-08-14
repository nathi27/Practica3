package com.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionMysql {// En esta clase se establece la conexión con la base de datos 

    private final String base = "practica3"; // se crea una variable con el nombre de la tabla 
    private final String user = "root"; // se crea una variable con el usuario de la base de datos 
    private final String password = "Bjvmvusdc0421#3"; // se crea una variable con la contraseña de la base de datos 
    private final String url = "jdbc:mysql://localhost:3306/" + base;// se crea una variable para almacenar 

    public Connection getConection() {// aca se crea una función para empezar la conexión con la base de datos 
        Connection con = null;// se crea una variable de tipo conexión vacia 
        try {// se encierran los argumentos en un try catch 
            Class.forName("com.mysql.cj.jdbc.Driver"); // se setea el nombre de la conexión 
            con = DriverManager.getConnection(url, user, password); // se establece la conexion con los parametros seteados anteriormente 
            System.out.println("Connection established!");// se imprime un mensaje que informa que la conexion fue exitosa
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading driver: " + e);  // de no serlo se informa 
        } catch (SQLException e) {
            System.err.println("Error connecting: " + e);
        }
        return con;
    }

}
