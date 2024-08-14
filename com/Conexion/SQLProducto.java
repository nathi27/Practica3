/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Conexion;

import com.Conexion.ConexionMysql;
import com.Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLProducto extends ConexionMysql {// esta clase es la que extiende o hereda de la conexión SQL

    ConexionMysql con = new ConexionMysql(); // se crean las variables necesarias para la conexión
    Connection cn = con.getConection();

    public boolean registrarProducto(Producto p) {
        String sql = "INSERT INTO producto(nombre, cantidad, precio, tipo) VALUES(?,?,?,?)"; // Se prepara un string para enviar la información al servidor
        PreparedStatement ps = null;

        try {
            if (cn != null) { // Si la conexión no es nula
                ps = cn.prepareStatement(sql); // Se setea la conexión y la información que va en la misma
                ps.setString(1, p.getNombre()); // Se instancian los valores que se necesitan
                ps.setInt(2, p.getCantidad());
                ps.setDouble(3, p.getPrecio());
                ps.setString(4, p.getTipo());
                ps.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Registrar el mensaje de error para su diagnóstico
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close(); // Cerrar PreparedStatement
                }
                if (cn != null) {
                    cn.close(); // Cerrar la conexión
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Registrar cualquier error al cerrar los recursos
            }
        }
    }

    public ResultSet obtenerProductos() {// esta funcion es para obtener los productos de la base de datos 
        String sql = "SELECT id, tipo, nombre, cantidad, precio FROM producto";// se crea una sentencia 
        PreparedStatement ps = null;// se prepara un state ment para enviar la informacion mas adelante
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

    public ResultSet obtenerProductoTipo(String tipo) {//este metodo se crea para obtener los productos por el tipo de producto
        String sql = "SELECT id, tipo, nombre, cantidad, precio FROM producto WHERE tipo = ?"; // aca se busca por medio del tipo y lo que se hace es que para la funcion es necseario un String el cual se declara en el llamado de la funcino en la vista
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setString(1, tipo); //aca se setea el parametro a utilizar
                rs = ps.executeQuery();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public boolean actualizarProducto(Producto producto, int id) {// mediante esta funcion se actualiza la informacion de un producto
        String sql = "UPDATE producto SET tipo = ?, nombre = ?, cantidad = ?, precio = ? WHERE id = ?"; // esto se realiza mediante el id

        if (cn == null) { //si no hay conecion se indica que la conexion no fue exitosa
            System.out.println("No se pudo establecer la conexión a la base de datos.");
            return false;
        }

        try (PreparedStatement pst = cn.prepareStatement(sql)) {// se crea el statement con el parametro sql
            pst.setString(1, producto.getTipo()); // se setean los valores de cada parametro o cada "?"
            pst.setString(2, producto.getNombre());
            pst.setInt(3, producto.getCantidad());
            pst.setDouble(4, producto.getPrecio());
            pst.setInt(5, id);// aca se pasa el id para comparar al momento de actualizar por el click del mouse 

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean registrar(Producto mod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
