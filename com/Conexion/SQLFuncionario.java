/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Conexion;

import com.Modelo.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLFuncionario extends ConexionMysql {

    ConexionMysql con = new ConexionMysql(); // se crean las variables necesarias para la conexión
    Connection cn = con.getConection(); 

    public boolean registrar(Funcionario funcionario) { // contiene los datos del funcionario a registrar
        // Consulta SQL para insertar un nuevo funcionario
        String sql = "INSERT INTO funcionario (id, nombre, password) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            // Establece la conexión a la base de datos
            con = getConection();

            if (con != null) {
                // Prepara la consulta con los datos del funcionario
                ps = con.prepareStatement(sql);
                
                ps.setString(1, funcionario.getid());
                ps.setString(2, funcionario.getNombre());
                ps.setString(3, funcionario.getPassword());
                // Ejecuta la consulta de inserción
                ps.executeUpdate();
                return true;
            } else {
                //Si la conexion es nula, se registra un error en el log
                Logger.getLogger(SQLFuncionario.class.getName()).log(Level.SEVERE, "Conexión a la base de datos no disponible.");
                return false;
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(SQLFuncionario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(SQLFuncionario.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    //  Método para obtener una lista de los funcionarios registrados en la base de datos
    public ResultSet obtenerFuncionarios() {
        // Consulta SQL para seleccionar los datos de los funcionarios 
        String sql = "SELECT id, nombre FROM funcionario";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Verifica si la conexión a la base de datos está disponible
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                // Ejecuta la consulta y obtiene los resultados
                rs = ps.executeQuery();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

}

