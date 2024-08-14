/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Controlador;

import com.Conexion.ConexionMysql;
import com.Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogicaProductoControlador {
    
    ConexionMysql con = new ConexionMysql(); // se crean las variables necesarias para la coneccion
    Connection cn = con.getConection();

    public boolean registrarProducto(Producto producto) {

        String sql = "INSERT INTO producto(nombre, cantidad, precio, tipo) VALUES(?,?,?,?)";
        PreparedStatement ps = null;

        try {
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setString(1, producto.getNombre());
                ps.setInt(2, producto.getCantidad());
                ps.setDouble(3, producto.getPrecio());
                ps.setString(4, producto.getTipo());
                ps.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean actualizarProducto(Producto producto, int id) {// mediante esta funcion se actualiza la informacion de un articulo
        String sql = "UPDATE producto SET tipo = ?, nombre = ?, cantidad = ?, precio = ? WHERE id = ?"; // esto se realiza meiante el id

        if (cn == null) { //si no hay conecion se indica que la coneccino no fue exitosa
            System.out.println("No se pudo establecer la conexión a la base de datos.");
            return false;
        }

        try (PreparedStatement pst = cn.prepareStatement(sql)) {// se crea el statement con el parametro sql
            pst.setString(1, producto.getTipo()); // se setean los valores de cada parametro o cada ?
            pst.setString(2, producto.getNombre());
            pst.setInt(3, producto.getCantidad());
            pst.setDouble(4, producto.getPrecio());
            pst.setInt(5, id);// aca se pasa el id para comparar al momento de actulizar por el click del mouse 

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el artículo: " + e.getMessage());
            return false;
        }
    }
    
    private ResultSet obtenerProducto() {// esta funcino es para obtener los articulos de la base de datos 
        String sql = "SELECT id, tipo, nombre, cantidad, precio FROM articulos";// se crea una sentencia 
        PreparedStatement ps = null;// se prepara un state ment par enviar la informcion mas adelante
        ResultSet rs = null;

        try {
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                rs = ps.executeQuery();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
        
    
}
